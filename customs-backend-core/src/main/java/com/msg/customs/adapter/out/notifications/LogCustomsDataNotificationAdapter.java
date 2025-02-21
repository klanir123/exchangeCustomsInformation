package com.msg.customs.adapter.out.notifications;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.msg.customs.adapter.out.notifications.json.NotificationJsonMapper;
import com.msg.customs.adapter.out.notifications.model.NotificationData;
import com.msg.customs.common.config.Adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Adapter
@RequiredArgsConstructor
@Slf4j
public class LogCustomsDataNotificationAdapter extends AbstractCustomsDataNotificationAdapter {
	
	private static final ObjectMapper objectMapper;
	
	static {
		 objectMapper = new ObjectMapper();
		 JavaTimeModule javaTimeModule = new JavaTimeModule();
	     javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	     objectMapper.registerModule(javaTimeModule);
	     objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	     objectMapper.setSerializationInclusion(Include.NON_NULL);	     
	}

	@Override
	protected void sendNotification(List<NotificationData> notifications) {
		for (NotificationData notification : notifications) {
			try {
				String jsonNotification = NotificationJsonMapper.toJson(notification);
				log.info("{}", jsonNotification);
			} catch (Exception e) {
				log.error("Error while logging notification", e);
			}
			
		}
		
	}

}
