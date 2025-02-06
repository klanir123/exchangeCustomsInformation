package com.msg.customs.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.port.in.LoadCustomsDataQuery;
import com.msg.customs.application.port.in.LoadCustomsDataUseCase;
import com.msg.customs.application.port.in.SaveCustomsDataCommand;
import com.msg.customs.application.port.in.SaveCustomsDataUseCase;
import com.msg.customs.application.port.out.LoadCustomsDataPort;
import com.msg.customs.application.port.out.SaveCustomsDataPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomsDataService implements LoadCustomsDataUseCase, SaveCustomsDataUseCase {	
	
	private final LoadCustomsDataPort loadCustomsDataPort;
	
	private final SaveCustomsDataPort saveCustomsDataPort;
	

	@Override
	public List<CustomsData> loadCustomsData(LoadCustomsDataQuery queryCustomsData) {
		// validate query and filters
		// call CustomStorageService (probably with query filter)
		// return result
		return loadCustomsDataPort.loadCustomsData();
	}

	@Override
	public void saveCustomsData(SaveCustomsDataCommand commandCustomsData) {
		saveCustomsDataPort.saveCustomsData(commandCustomsData.getCustomsData());		
	}

}
