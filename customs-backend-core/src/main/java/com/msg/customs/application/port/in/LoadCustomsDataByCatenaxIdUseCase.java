package com.msg.customs.application.port.in;

import com.msg.customs.application.domain.model.CustomsData;

public interface LoadCustomsDataByCatenaxIdUseCase {

	CustomsData loadCustomsData(String catenaxId);
	
}
