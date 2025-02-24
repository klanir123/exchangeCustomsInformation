package com.msg.customs.adapter.out.notifications;

import java.util.List;

import com.msg.customs.adapter.out.notifications.json.NotificationJsonMapper;
import com.msg.customs.adapter.out.notifications.model.NotificationData;
import com.msg.customs.common.config.Adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Simple NotificationAdapter which simply logs the notification data (instead of calling e.g. the EDC).
 */
@Adapter
@RequiredArgsConstructor
@Slf4j
public class LogCustomsDataNotificationAdapter extends AbstractCustomsDataNotificationAdapter {
	
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
