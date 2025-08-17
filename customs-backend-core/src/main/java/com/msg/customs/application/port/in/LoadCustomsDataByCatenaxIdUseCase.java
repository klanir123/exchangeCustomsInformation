package com.msg.customs.application.port.in;

import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.domain.model.MasterPart;

/**
 * UseCase to load customs data by a catenax id.
 */
public interface LoadCustomsDataByCatenaxIdUseCase {

	CustomsData loadCustomsData(String catenaxId);

	MasterPart loadMasterPart(String catenaXId);
	
}
