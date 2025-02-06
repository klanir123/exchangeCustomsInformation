package com.msg.customs.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.port.in.LoadCustomsDataQuery;
import com.msg.customs.application.port.in.LoadCustomsDataUseCase;
import com.msg.customs.application.port.in.SaveCustomsDataCommand;
import com.msg.customs.application.port.in.SaveCustomsDataUseCase;
import com.msg.customs.application.port.out.CustomsDataStorageAdapter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomsDataService implements LoadCustomsDataUseCase, SaveCustomsDataUseCase {	
	
	private final CustomsDataStorageAdapter customsStorageAdapter;
	
	@Override
	public List<CustomsData> loadCustomsData(LoadCustomsDataQuery queryCustomsData) {
		// validate query and filters
		// call CustomStorageService (probably with query filter)
		// return result
		return customsStorageAdapter.loadCustomsData();
	}

	@Override
	public void saveCustomsData(SaveCustomsDataCommand commandCustomsData) {
		customsStorageAdapter.saveCustomsData(commandCustomsData.getCustomsData());		
	}

}
