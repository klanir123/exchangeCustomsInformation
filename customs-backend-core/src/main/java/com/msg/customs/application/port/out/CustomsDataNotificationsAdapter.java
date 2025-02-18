package com.msg.customs.application.port.out;

import java.util.List;

import com.msg.customs.application.domain.model.CustomsData;

public interface CustomsDataNotificationsAdapter {
	
	void notifyCustomsDataChanged(List<CustomsData> data);

}
