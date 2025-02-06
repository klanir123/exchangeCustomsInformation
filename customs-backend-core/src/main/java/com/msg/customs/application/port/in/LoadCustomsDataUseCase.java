package com.msg.customs.application.port.in;

import java.util.List;

import com.msg.customs.application.domain.model.CustomsData;

public interface LoadCustomsDataUseCase {
	
	List<CustomsData> loadCustomsData(LoadCustomsDataQuery queryCustomsData);
	
	default LoadCustomsDataQuery queryAll() {
		return new LoadCustomsDataQuery();
	}

}
