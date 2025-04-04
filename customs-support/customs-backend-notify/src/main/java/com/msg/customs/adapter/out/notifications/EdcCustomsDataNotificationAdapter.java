package com.msg.customs.adapter.out.notifications;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.msg.customs.adapter.out.notifications.api.json.JsonUtil;
import com.msg.customs.adapter.out.notifications.api.model.*;
import com.msg.customs.adapter.out.notifications.config.RestProperties;
import com.msg.customs.adapter.out.notifications.json.NotificationJsonMapper;
import com.msg.customs.adapter.out.notifications.model.*;
import com.msg.customs.adapter.out.notifications.service.ContractSelectionService;
import com.msg.customs.adapter.out.notifications.service.NegotiationStatusChecker;
import com.msg.customs.adapter.out.notifications.service.RestService;
import com.msg.customs.common.config.Adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Simple NotificationAdapter which simply logs the notification data (instead of calling e.g. the EDC).
 */
@Adapter
@RequiredArgsConstructor
@Slf4j
public class EdcCustomsDataNotificationAdapter extends AbstractCustomsDataNotificationAdapter {

	private final RestProperties restProperties;

	private final RestService restService;

	private final ContractSelectionService contractSelectionService;

	@Override
	protected EDCEndpoint retrievEDCEndpoint(BPN businessPartnerNumber) {
		// TODO Call Discovery Service to get the edc endpoint for the target business partner number
		return EDCEndpoint.builder().endpoint(restProperties.getEndpoint()).build();
	}

	@Override
	protected ContractOffer retrieveContract(EDCEndpoint endpoint, BPN senderBPN) {
		CatalogRequest catalogRequest = CatalogRequest.withDefaults().counterPartyAddress(endpoint.getEndpoint()).build();
		ResponseEntity<String> response = restService.fetchCatalog().callEntity(catalogRequest);

		if (response.getStatusCode() == HttpStatus.OK) {
			log.atInfo().addArgument(response).log("Catalog fetched successfully {}");
			CatalogResponse catalogResponse = JsonUtil.fromJson(response.getBody(), CatalogResponse.class);
			// filter catalog and find customs contract offer
			ContractOffer customsOffer = contractSelectionService.selectCustomsOffer(catalogResponse);
			if (customsOffer != null) {
				log.info("Customs offer found in catalog");
				log.info(customsOffer.toString());
				// negotiate customs contract
				return customsOffer;
			} else {
				log.error("No customs offer found in catalog");
			}
		} else {
			log.error("Error while fetching catalog: {}", response.getStatusCode());
		}
		// TODO -> used to avoid error
		return ContractOffer.builder().build();
	}

	@Override
	protected ContractAgreement initiateContractNegotiation(EDCEndpoint endPoint, ContractOffer offer) {
		NegotiationRequest request = NegotiationRequest
				.withDefaults()
				.counterPartyAddress(endPoint.getEndpoint())
				.policy(offer.toPolicy())
				.build();

		ResponseEntity<String> responseEntity = restService.initiateContractNegotiation().callEntity(request);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			log.info("Contract negotiation initiated successfully");
			NegotiationResponse response = JsonUtil.fromJson(responseEntity.getBody(), NegotiationResponse.class);
			NegotiationStatusChecker statusChecker = new NegotiationStatusChecker(restService);

			try {
				Future<ContractAgreement> future = statusChecker.startCheckingNegotiationStatus(response.getId());
				ContractAgreement agreement = future.get(); // Wait for the negotiation to complete
				log.info("Negotiation completed successfully: {}", agreement);
				return agreement;
				// Process the agreement as needed
			} catch (InterruptedException | ExecutionException e) {
				log.error("Error while waiting for negotiation to complete", e);
			}

		} else {
			log.error("Error while initiating contract negotiation: {}", responseEntity.getStatusCode());
		}
		// TODO -> used to avoid error
		return ContractAgreement.builder().build();
	}

	@Override
	protected DataTransferConnection initiateDataTransfer(EDCEndpoint endPoint, ContractAgreement agreement) {
		// TODO Call EDC to initiate the data transfer
		return new DataTransferConnection();
	}

	@Override
	protected void sendNotification(DataTransferConnection connection, List<NotificationData> notifications) {
		for (NotificationData notification : notifications) {
			try {
				String jsonNotification = NotificationJsonMapper.toJson(notification);
				// TODO Call EDC and send custom information to receiver.
				// Do we need to send every custom information in a separate notification?
				// For now, just log the notification
				log.info("{}", jsonNotification);
			} catch (Exception e) {
				log.error("Error while logging notification", e);
			}
		}
	}

	@Override
	protected BPN getSenderBPN() {
		return BPN.of("Bob-123-456-789");
	}
}
