package com.msg.customs.application.port.in;

import java.util.List;

/**
 * Use case to send notifications about changed customs data.
 */
public interface NotifyCustomsDataChangedUseCase {

	/**
	 * Notifies the customs data changed.
	 * @param catenaxId the catenax id of the customs data
	 */
	void notifyCustomsDataChanged(List<String> catenaxId);
}
