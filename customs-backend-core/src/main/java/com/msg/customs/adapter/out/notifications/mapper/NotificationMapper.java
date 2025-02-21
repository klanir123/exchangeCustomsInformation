package com.msg.customs.adapter.out.notifications.mapper;

import com.msg.customs.adapter.out.notifications.model.NotificationContactData;
import com.msg.customs.adapter.out.notifications.model.NotificationCustomsData;
import com.msg.customs.adapter.out.notifications.model.NotificationData;
import com.msg.customs.adapter.out.notifications.model.NotificationPart;
import com.msg.customs.adapter.out.notifications.model.NotificationTradeWorth;
import com.msg.customs.application.domain.model.CustomsData;

public class NotificationMapper {

	
	public static NotificationData toNotification(CustomsData data) {
		NotificationData notification = new NotificationData();

		notification.setCustomsData(toNotificationCustomsData(data));
		notification.setPartData(toNotificationPartData(data));

		return notification;
	}
	
	public static NotificationCustomsData toNotificationCustomsData(CustomsData data) {
		NotificationCustomsData notification = new NotificationCustomsData();

		notification.setCatenaxId(data.getCatenaxId());
		
		notification.setType(data.getType());
		notification.setConfirmationDate(data.getConfirmationDate());
		notification.setValidFrom(data.getValidFrom());
				
		notification.setContactInformation(toNotificationContactData(data));
		
		notification.setCommercialCountryOfOrigin(data.getCountry());
		notification.setFederalState(data.getFederalState());
		notification.setOriginInformation(data.getOriginInformation());
		notification.setPreferredStatus(data.getPrefferedStatus());
		
		notification.setTradeWorth(toNotificationTradeWorthData(data));

		return notification;
	}
	
	
	public static NotificationPart toNotificationPartData(CustomsData data) {
		NotificationPart notification = new NotificationPart();
		
		notification.setCustomerPartId(data.getCustomerPart().getNumber());
		notification.setNameAtCustomer(data.getCustomerPart().getName());
		notification.setManufacturerPartId(data.getManufacturerPart().getNumber());
		notification.setNameAtManufacturer(data.getManufacturerPart().getName());
				
		return notification;
	}
	
	public static NotificationTradeWorth toNotificationTradeWorthData(CustomsData data) {
		NotificationTradeWorth notification = new NotificationTradeWorth();

		if (data.getTradeWorth() == null) {
			return null;
		}
		
		if (data.getTradeWorth().getValue() != null) {
			notification.setValue(Double.valueOf(data.getTradeWorth().getValue()));			
		}
		notification.setCurrency(data.getTradeWorth().getCurrency());

		return notification;
	}
	
	public static NotificationContactData toNotificationContactData(CustomsData data) {
		NotificationContactData notification = new NotificationContactData();

		notification.setContactPerson(data.getContact().getName());
		notification.setPhoneNumber(data.getContact().getPhone());
		notification.setEmail(data.getContact().getEmail());
		notification.setJobTitle(data.getContact().getJobTitle());
				
		return notification;
	}

	
}
