package com.msg.customs.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.port.in.LoadCustomsDataByCatenaxIdUseCase;
import com.msg.customs.application.port.in.LoadCustomsDataListQuery;
import com.msg.customs.application.port.in.LoadCustomsDataListUseCase;
import com.msg.customs.application.port.in.SaveCustomsDataCommand;
import com.msg.customs.application.port.in.SaveCustomsDataUseCase;
import com.msg.customs.application.port.out.CustomsDataStorageAdapter;

import lombok.RequiredArgsConstructor;

/**
 * Service to manage customs data storage UseCases.
 */
@RequiredArgsConstructor
@Service
public class CustomsDataService implements LoadCustomsDataListUseCase, SaveCustomsDataUseCase, LoadCustomsDataByCatenaxIdUseCase {	
	
	private final CustomsDataStorageAdapter customsStorageAdapter;
	
	@Override
	public List<CustomsData> loadCustomsData(LoadCustomsDataListQuery queryCustomsData) {
		// validate query and filters
		// call CustomStorageService (probably with query filter)
		// return result
		return customsStorageAdapter.loadCustomsData();
	}

	@Override
	public CustomsData loadCustomsData(String catenaxId) {
		return customsStorageAdapter.loadCustomsDataByCatenaxId(catenaxId);
	}
	
	@Override
	public void saveCustomsData(SaveCustomsDataCommand commandCustomsData) {
		customsStorageAdapter.saveCustomsData(commandCustomsData.getCustomsData());		
	}

	@Override
	public void saveMasterPartData(SaveCustomsDataCommand masterPartData) {
		customsStorageAdapter.saveMasterPartData(masterPartData.getMasterPart());
	}

}
