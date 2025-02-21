package com.msg.customs.adapter.out.notifications;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.msg.customs.adapter.out.notifications.mapper.NotificationMapper;
import com.msg.customs.adapter.out.notifications.model.NotificationData;
import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.port.out.CustomsDataNotificationsAdapter;

public abstract class AbstractCustomsDataNotificationAdapter implements CustomsDataNotificationsAdapter {

	private static final ObjectMapper OBJECT_MAPPER;
	
	static {
		 OBJECT_MAPPER = new ObjectMapper();
		 JavaTimeModule javaTimeModule = new JavaTimeModule();
	     javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	     OBJECT_MAPPER.registerModule(javaTimeModule);
	     OBJECT_MAPPER.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	     OBJECT_MAPPER.setSerializationInclusion(Include.NON_NULL);	     
	}
	
	@Override
	public void notifyCustomsDataChanged(List<CustomsData> data) {
		List<NotificationData> notifications = data.stream().map(NotificationMapper::toNotification).toList();
		sendNotification(notifications); 
	}
	
	protected String toJson(Object object) {
		try {
			return OBJECT_MAPPER.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException("Error while converting object to json", e);
		}
	}
	
	protected abstract void sendNotification(List<NotificationData> notifications);

}
