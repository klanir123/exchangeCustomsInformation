package com.msg.customs.adapter.out.notifications;

import java.util.List;

import com.msg.customs.adapter.out.notifications.mapper.NotificationMapper;
import com.msg.customs.adapter.out.notifications.model.NotificationData;
import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.port.out.CustomsDataNotificationsAdapter;

public abstract class AbstractCustomsDataNotificationAdapter implements CustomsDataNotificationsAdapter {

	/**
	 * Notifies the customs data changed.
	 */
	@Override
	public void notifyCustomsDataChanged(List<CustomsData> data) {
		List<NotificationData> notifications = data.stream().map(NotificationMapper::toNotification).toList();
		sendNotification(notifications); 
	}
		
	/**
	 * Sends the notification.
	 * 
	 * @param notifications the notifications
	 */
	protected abstract void sendNotification(List<NotificationData> notifications);

}
