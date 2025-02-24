package com.msg.customs.adapter.out.notifications.model;

import lombok.Data;

/**
 * Wrapper to split the part number data and the the rest of the customs data.
 */
@Data
public class NotificationData {

	private NotificationCustomsData customsData;
	
	private NotificationPart partData;
	
}
