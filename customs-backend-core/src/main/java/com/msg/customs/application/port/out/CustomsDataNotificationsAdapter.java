package com.msg.customs.application.port.out;

import java.util.List;

import com.msg.customs.application.domain.model.CustomsData;

/**
 * Adapter to notify about customs data changes.
 */
public interface CustomsDataNotificationsAdapter {
	
	/**
	 * Notifies the customs data changed.
	 * @param data the customs data that changed
	 */
	void notifyCustomsDataChanged(List<CustomsData> data);

}
