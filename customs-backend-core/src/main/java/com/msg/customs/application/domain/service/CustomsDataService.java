package com.msg.customs.application.domain.service;

import java.util.List;

import com.msg.customs.application.domain.model.MasterPart;
import com.msg.customs.application.domain.model.Task;
import com.msg.customs.application.port.in.*;
import org.springframework.stereotype.Service;

import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.port.out.CustomsDataStorageAdapter;

import lombok.RequiredArgsConstructor;

/**
 * Service to manage customs data storage UseCases.
 */
@RequiredArgsConstructor
@Service
public class CustomsDataService implements LoadCustomsDataListUseCase, LoadMasterPartDataListUseCase, LoadTaskDataListUseCase, SaveCustomsDataUseCase, LoadCustomsDataByCatenaxIdUseCase{
	
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
	public MasterPart loadMasterPart(String catenaxId) {
		return customsStorageAdapter.loadMasterPartDataByCatenaxId(catenaxId);
	}
	
	@Override
	public void saveCustomsData(SaveCustomsDataCommand commandCustomsData) {
		customsStorageAdapter.saveCustomsData(commandCustomsData.getCustomsData());		
	}

	@Override
	public void saveMasterPartData(SaveCustomsDataCommand masterPartData) {
		customsStorageAdapter.saveMasterPartData(masterPartData.getMasterPart());
	}

	@Override
	public List<MasterPart> loadMasterPartData(LoadMasterPartListQuery queryMasterPartData) {
		return customsStorageAdapter.loadMasterPartData();
	}

	@Override
	public List<Task> loadTaskData(LoadTaskListQuery queryTaskData) {
		return customsStorageAdapter.loadTaskData();
	}

}
