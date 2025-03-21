package com.msg.customs.adapter.out.notifications.json;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.msg.customs.adapter.out.notifications.model.NotificationData;

/**
 * Special mapper to map the NotificationData to Json representation. 
 */
public class NotificationJsonMapper {

	private static final ObjectMapper OBJECT_MAPPER;
	
	/**
	 * Some useful configuration for the ObjectMapper. 
	 * <p>
	 * Be aware that we use a static implementation here. Please consider to use a more dynamic approach.
	 */
	static {
		 OBJECT_MAPPER = new ObjectMapper();
		 JavaTimeModule javaTimeModule = new JavaTimeModule();
	     javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	     OBJECT_MAPPER.registerModule(javaTimeModule);
	     OBJECT_MAPPER.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	     OBJECT_MAPPER.setSerializationInclusion(Include.NON_NULL);	     
	}
	
	/**
	 * Convert the NotificationData to Json.
	 * @param notificationData the notification data
	 * @return the Json representation of the notification data
	 * @throws JsonProcessingException
	 */
	public static String toJson(NotificationData notificationData) throws JsonProcessingException {
		return OBJECT_MAPPER.writeValueAsString(notificationData);
	}
	
}
