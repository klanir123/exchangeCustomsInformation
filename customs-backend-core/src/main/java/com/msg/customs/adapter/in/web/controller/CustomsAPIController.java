package com.msg.customs.adapter.in.web.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.msg.customs.adapter.in.web.CustomsAPI;
import com.msg.customs.adapter.in.web.mapper.CustomsDataValueMapper;
import com.msg.customs.adapter.in.web.model.CustomsDataValue;
import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.port.in.LoadCustomsDataQuery;
import com.msg.customs.application.port.in.LoadCustomsDataUseCase;
import com.msg.customs.application.port.in.SaveCustomsDataCommand;
import com.msg.customs.application.port.in.SaveCustomsDataUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CustomsAPIController implements CustomsAPI {

	private final LoadCustomsDataUseCase loadCustomsDataUseCase;
	private final SaveCustomsDataUseCase saveCustomsDataUseCase;
	
	@Override
	public ResponseEntity<List<CustomsDataValue>> getCustomsData() {
		List<CustomsData> customsData = loadCustomsDataUseCase.loadCustomsData(new LoadCustomsDataQuery());
		return ResponseEntity.ok(customsData.stream().map(CustomsDataValueMapper::toValue).toList());
	}

	@Override
	public ResponseEntity<CustomsDataValue> getCustomsData(String catenaxId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<CustomsDataValue> deleteCustomsData(String catenaxId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> saveCustomsData(CustomsDataValue customsDataRecord) {
		log.info(customsDataRecord.toString());
		CustomsData customsData = CustomsDataValueMapper.toDomain(customsDataRecord);
		saveCustomsDataUseCase.saveCustomsData(SaveCustomsDataCommand.builder().customsData(customsData).build());
		return ResponseEntity.ok().build();
	}



}
