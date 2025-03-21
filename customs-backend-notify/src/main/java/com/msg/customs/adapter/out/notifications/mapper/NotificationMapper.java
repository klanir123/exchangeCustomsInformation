package com.msg.customs.adapter.out.notifications.mapper;

import com.msg.customs.adapter.out.notifications.model.*;
import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.domain.model.Part;

import java.util.List;

/**
 * Mapper to map the customs data to the notification data.
 */
public class NotificationMapper {

	/**
	 * Maps the list of customs data to the notification data.
	 * @param listOfCustomsData the list of customs data
	 * @return the notification data (containing the customs update information).
	 */
	public static NotificationData toNotification(List<CustomsData> listOfCustomsData) {
		NotificationData notifications = new NotificationData();

		notifications.setItems(listOfCustomsData.stream().map(NotificationMapper::toNotificationItemData).toList());

		return notifications;
	}

	/**
     * Maps the customs data to the notification data.
     * 
     * @param data the customs data
     * @return the notification data.
     */
	public static NotificationItemData toNotificationItemData(CustomsData data) {
		NotificationItemData notification = new NotificationItemData();

		notification.setCatenaxId(data.getCatenaxId());

		notification.setCustomsData(toNotificationCustomsData(data));
		notification.setCustomerPart(toNotificationCustomerPartData(data));
		notification.setManufacturerPart(toNotificationManufacturerPartData(data));

		return notification;
	}
	
	/**
	 * Maps the customs data to the notification representing some of the customs data.
	 */
	public static NotificationCustomsData toNotificationCustomsData(CustomsData data) {
		NotificationCustomsData notification = new NotificationCustomsData();
		
		notification.setType(data.getType());
		notification.setConfirmationDate(data.getConfirmationDate());
		notification.setValidFrom(data.getValidFrom());
				
		notification.setContactInformation(toNotificationContactData(data));
		
		notification.setCommercialCountryOfOrigin(data.getCountry());
		notification.setFederalState(data.getFederalState());
		if (data.getOriginInformation() != null && data.getOriginInformation().size() > 0) {
			notification.setOriginInformation(data.getOriginInformation());
		}
		notification.setPreferredStatus(data.getPreferredStatus());
		
		notification.setTracedWorth(toNotificationTracedWorthData(data));

		notification.setStatus(data.getRequestStatus());

		return notification;
	}
	
	/**
	 * Maps the customs data to the notification representing the part data.
	 */	
	public static NotificationPart toNotificationCustomerPartData(CustomsData data) {
		return toNotificationPartData(data.getCustomerPart());
	}

	/**
	 * Maps the customs data to the notification representing the part data.
	 */
	public static NotificationPart toNotificationManufacturerPartData(CustomsData data) {
		return toNotificationPartData(data.getManufacturerPart());
	}

	/**
	 * Maps the customs data to the notification representing the part data.
	 */
	public static NotificationPart toNotificationPartData(Part partData) {
		if (partData == null || partData.isEmpty()) {
			return null;
		}

		NotificationPart notification = new NotificationPart();
		notification.setId(partData.getNumber());
		notification.setName(partData.getName());
		return notification;
	}


	/**
	 * Maps the customs data to the notification representing the part trade worth.
	 */		
	public static NotificationTracedWorth toNotificationTracedWorthData(CustomsData data) {
		NotificationTracedWorth notification = new NotificationTracedWorth();

		if (data.getTracedWorth() == null || data.getTracedWorth().isEmpty()) {
			return null;
		}
		
		if (data.getTracedWorth().getValue() != null) {
			notification.setValue(Double.valueOf(data.getTracedWorth().getValue()));
		}
		notification.setCurrency(data.getTracedWorth().getCurrency());

		return notification;
	}
	
	/**
	 * Maps the customs data to the notification representing the contact data.
	 */				
	public static NotificationContactData toNotificationContactData(CustomsData data) {
		NotificationContactData notification = new NotificationContactData();

		notification.setContactPerson(data.getContact().getName());
		notification.setPhoneNumber(data.getContact().getPhone());
		notification.setEmail(data.getContact().getEmail());
		notification.setJobTitle(data.getContact().getJobTitle());
				
		return notification;
	}

	
}
