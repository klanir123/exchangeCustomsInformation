package com.msg.customs.adapter.in.web.controller;


import java.util.List;

import com.msg.customs.adapter.in.web.mapper.MasterPartValueMapper;
import com.msg.customs.adapter.in.web.model.MasterPartDto;
import com.msg.customs.application.domain.model.MasterPart;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.msg.customs.adapter.in.web.CustomsAPI;
import com.msg.customs.adapter.in.web.mapper.CustomsDataValueMapper;
import com.msg.customs.adapter.in.web.model.CustomsDataDto;
import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.port.in.LoadCustomsDataByCatenaxIdUseCase;
import com.msg.customs.application.port.in.LoadCustomsDataListQuery;
import com.msg.customs.application.port.in.LoadCustomsDataListUseCase;
import com.msg.customs.application.port.in.NotifyCustomsDataChangedUseCase;
import com.msg.customs.application.port.in.SaveCustomsDataCommand;
import com.msg.customs.application.port.in.SaveCustomsDataUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for customs data.
 * <p>
 * A controller used the UseCases to call the business logic (DDD).
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class CustomsAPIController implements CustomsAPI {

	private final LoadCustomsDataListUseCase loadCustomsDataUseCase;
	private final SaveCustomsDataUseCase saveCustomsDataUseCase;
	private final LoadCustomsDataByCatenaxIdUseCase loadCustomsDataByCatenaxIdUseCase;
	private final NotifyCustomsDataChangedUseCase notifyCustomsDataChangedUseCase;
	
	@Override
	public ResponseEntity<List<CustomsDataDto>> getCustomsData() {
		List<CustomsData> customsData = loadCustomsDataUseCase.loadCustomsData(LoadCustomsDataListQuery.queryAll());
		return ResponseEntity.ok(customsData.stream().map(CustomsDataValueMapper::toDto).toList());
	}

	@Override
	public ResponseEntity<CustomsDataDto> getCustomsData(String id) {
		CustomsData customsData = loadCustomsDataByCatenaxIdUseCase.loadCustomsData(id);
		return ResponseEntity.ok(CustomsDataValueMapper.toDto(customsData));
	}

	@Override
	public ResponseEntity<CustomsDataDto> deleteCustomsData(String catenaxId) {
		// TODO Auto-generated method stub
		return null;
	}



    @Override
	public ResponseEntity<Void> saveCustomsData(CustomsDataDto customsDataValue) {
		log.info(customsDataValue.toString());
		CustomsData customsData = CustomsDataValueMapper.toDomain(customsDataValue);
		saveCustomsDataUseCase.saveCustomsData(SaveCustomsDataCommand.builder().customsData(customsData).build());
		return ResponseEntity.ok().build();
	}

    @Override
    public ResponseEntity<Void> saveMasterPart(MasterPartDto masterPartData) {
		log.info(masterPartData.toString());
		MasterPart masterPart = MasterPartValueMapper.toDomain(masterPartData);

		saveCustomsDataUseCase.saveMasterPartData(SaveCustomsDataCommand.builder().masterPart(masterPart).build());
		return ResponseEntity.ok().build();

    }

    @Override
	public ResponseEntity<Void> notifyCustomsDataUpdated(List<String> catenaxIds) { 
		notifyCustomsDataChangedUseCase.notifyCustomsDataChanged(getTargetBPN(), catenaxIds);
		return ResponseEntity.ok().build();
	}

	/**
	 * Returns a (fixed) target business partner number. For producion purpose this needs to be done mor flexible.
	 * @return a target business partner number
	 */
	private String getTargetBPN() {
		return "ALICE-987-654-321";
	}
}
