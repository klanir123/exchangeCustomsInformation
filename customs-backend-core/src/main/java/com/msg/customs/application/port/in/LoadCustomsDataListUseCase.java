package com.msg.customs.application.port.in;

import java.util.List;

import com.msg.customs.application.domain.model.CustomsData;

public interface LoadCustomsDataListUseCase {
	
	List<CustomsData> loadCustomsData(LoadCustomsDataListQuery queryCustomsData);
	
	default LoadCustomsDataListQuery queryAll() {
		return new LoadCustomsDataListQuery();
	}

}
