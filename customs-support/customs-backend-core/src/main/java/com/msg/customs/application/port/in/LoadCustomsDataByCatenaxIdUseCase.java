package com.msg.customs.application.port.in;

import com.msg.customs.application.domain.model.CustomsData;

/**
 * UseCase to load customs data by a catenax id.
 */
public interface LoadCustomsDataByCatenaxIdUseCase {

	CustomsData loadCustomsData(String catenaxId);
	
}
