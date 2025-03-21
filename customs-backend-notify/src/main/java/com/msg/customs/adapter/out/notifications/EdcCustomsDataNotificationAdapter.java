package com.msg.customs.adapter.out.notifications;

import java.util.List;

import com.msg.customs.adapter.out.notifications.json.NotificationJsonMapper;
import com.msg.customs.adapter.out.notifications.model.*;
import com.msg.customs.common.config.Adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Simple NotificationAdapter which simply logs the notification data (instead of calling e.g. the EDC).
 */
@Adapter
@RequiredArgsConstructor
@Slf4j
public class EdcCustomsDataNotificationAdapter extends AbstractCustomsDataNotificationAdapter {

	@Override
	protected EDCEndpoint retrievEDCEndpoint(BPN businessPartnerNumber) {
		// TODO Call Discovery Service to get the edc endpoint for the target business partner number
		return new EDCEndpoint();
	}

	@Override
	protected Contract retrieveContract(EDCEndpoint endpoint, BPN senderBPN) {
		// TODO Call EDC to get the contracts provided by the receiver
		// TODO Select the contract relevant for customs information
		return new Contract();
	}

	@Override
	protected Negotiation initiateContractNegotiation(EDCEndpoint endPoint, Contract contract) {
		// TODO Call EDC to initiate the contract negotiation
		return new Negotiation();
	}

	@Override
	protected DataTransferConnection initiateDataTransfer(EDCEndpoint endPoint, Negotiation negotiation) {
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
