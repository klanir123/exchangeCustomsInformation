package com.msg.customs.adapter.in.web.controller;


import java.util.List;

import com.msg.customs.adapter.in.web.mapper.MasterPartValueMapper;
import com.msg.customs.adapter.in.web.mapper.TaskValueMapper;
import com.msg.customs.adapter.in.web.model.MasterPartDto;
import com.msg.customs.adapter.in.web.model.TaskDto;
import com.msg.customs.application.domain.model.MasterPart;
import com.msg.customs.application.domain.model.Task;
import com.msg.customs.application.port.in.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msg.customs.adapter.in.web.CustomsAPI;
import com.msg.customs.adapter.in.web.mapper.CustomsDataValueMapper;
import com.msg.customs.adapter.in.web.model.CustomsDataDto;
import com.msg.customs.application.domain.model.CustomsData;

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
	private final LoadMasterPartDataListUseCase loadMasterPartDataListUseCase;
	private final SaveCustomsDataUseCase saveCustomsDataUseCase;
	private final LoadCustomsDataByCatenaxIdUseCase loadCustomsDataByCatenaxIdUseCase;
	private final NotifyCustomsDataChangedUseCase notifyCustomsDataChangedUseCase;
	private final LoadTaskDataListUseCase loadTaskDataListUseCase;
	
	@Override
	public ResponseEntity<List<CustomsDataDto>> getCustomsData() {
		List<CustomsData> customsData = loadCustomsDataUseCase.loadCustomsData(LoadCustomsDataListQuery.queryAll());
		return ResponseEntity.ok(customsData.stream().map(CustomsDataValueMapper::toDto).toList());
	}



	@Override
	public ResponseEntity<List<MasterPartDto>> getMasterPartsData() {
		List<MasterPart> masterParts = loadMasterPartDataListUseCase.loadMasterPartData(LoadMasterPartListQuery.queryAll());
		return ResponseEntity.ok(masterParts.stream().map(MasterPartValueMapper::toDto).toList());
	}

	@GetMapping("/tasks")
	@Override
	public ResponseEntity<List<TaskDto>> getTasksData() {
		List<Task> tasks = loadTaskDataListUseCase.loadTaskData(LoadTaskListQuery.queryAll());
		return ResponseEntity.ok(tasks.stream().map(TaskValueMapper::toDto).toList());
	}

	@GetMapping("/{id}")
	@Override
	public ResponseEntity<CustomsDataDto> getCustomsData(String id) {
		CustomsData customsData = loadCustomsDataByCatenaxIdUseCase.loadCustomsData(id);
		return ResponseEntity.ok(CustomsDataValueMapper.toDto(customsData));
	}

	@GetMapping("/part/{id}")
	@Override
	public ResponseEntity<MasterPartDto> getMasterPartData(String id) {
		MasterPart masterPart = loadCustomsDataByCatenaxIdUseCase.loadMasterPart(id);
		return ResponseEntity.ok(MasterPartValueMapper.toDto(masterPart));
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

	@Override
	public ResponseEntity<Void> deleteMasterParts(List<String> catenaxIds) {
		log.info("Deleting master parts with catenaxIds: {}", catenaxIds);
		saveCustomsDataUseCase.deleteMasterParts(catenaxIds);
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
