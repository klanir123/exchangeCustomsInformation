package com.msg.customs.adapter.out.notifications;

import java.util.List;

import com.msg.customs.adapter.out.notifications.mapper.NotificationMapper;
import com.msg.customs.adapter.out.notifications.model.*;
import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.port.out.CustomsDataNotificationsAdapter;

public abstract class AbstractCustomsDataNotificationAdapter implements CustomsDataNotificationsAdapter {

	/**
	 * Notifies the customs data changed.
	 */
	@Override
	public void notifyCustomsDataChanged(String targetBPN, List<CustomsData> data) {
		EDCEndpoint edcEndpoint = retrievEDCEndpoint(BPN.of(targetBPN));
		if (edcEndpoint == null) {
			throw new RuntimeException("No EDC endpoint found for business partner number: " + targetBPN);
		}
		ContractOffer contract = retrieveContract(edcEndpoint, getSenderBPN());
		if (contract == null) {
			throw new RuntimeException("No contract found for business partner number: " + targetBPN);
		}
		Negotiation negotiation = initiateContractNegotiation(edcEndpoint, contract);
		if (negotiation == null) {
			throw new RuntimeException("Negotiation could not be initiated for business partner number: " + targetBPN);
		}
		DataTransferConnection connection = initiateDataTransfer(edcEndpoint, negotiation);
		if (connection == null) {
			throw new RuntimeException("Data transfer could not be initiated for business partner number: " + targetBPN);
		}
		List<NotificationData> notifications = List.of(NotificationMapper.toNotification(data));
		sendNotification(connection, notifications);
	}

	/**
	 * Retrieves the EDC endpoint for the given business partner number.
	 * @param businessPartnerNumber the number of the business partner which should get customs information
	 * @return the EDC endpoint of the receiver
	 */
	protected abstract EDCEndpoint retrievEDCEndpoint(BPN businessPartnerNumber);

	/**
	 * Retrieves the contracts and select the contract relevant for customs information.
	 *
	 * @param endpoint                the EDC endpoint of the receiver
	 * @param myBusinessPartnerNumber the business partner number of the sender
	 * @return the contract or null if no contract is found
	 */
	protected abstract ContractOffer retrieveContract(EDCEndpoint endpoint, BPN myBusinessPartnerNumber);

	/**
	 * Initiates the contract negotiation.
	 * @param endPoint the EDC endpoint of the receiver
	 * @param contract the contract
	 * @return the negotiation or null if the negotiation could not be initiated
	 */
	protected abstract Negotiation initiateContractNegotiation(EDCEndpoint endPoint, ContractOffer contract);

	/**
	 * Establish data transfer connection.
	 * @param endPoint the EDC endpoint of the receiver
	 * @param negotiation the negotiation
	 * @return the data transfer or null if the data transfer could not be initiated
	 */
	protected abstract DataTransferConnection initiateDataTransfer(EDCEndpoint endPoint, Negotiation negotiation);

	/**
	 * Sends the notification.
	 *
	 * @param notifications the notifications
	 * @param connection the established connection
	 */
	protected abstract void sendNotification(DataTransferConnection connection, List<NotificationData> notifications);

	/**
	 *
	 * @return the BPN of the sender
	 */
	protected abstract BPN getSenderBPN();
}
