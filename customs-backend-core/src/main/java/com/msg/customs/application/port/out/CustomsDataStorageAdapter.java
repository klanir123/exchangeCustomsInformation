package com.msg.customs.application.port.out;

import java.util.List;

import com.msg.customs.application.domain.model.CustomsData;

public interface CustomsDataStorageAdapter {

	List<CustomsData> loadCustomsData();
	
	void saveCustomsData(CustomsData customsData);

	CustomsData loadCustomsDataByCatenaxId(String catenaxId);
}
