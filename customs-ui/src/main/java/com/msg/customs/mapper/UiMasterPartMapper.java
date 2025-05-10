package com.msg.customs.mapper;

import com.msg.customs.adapter.in.web.model.CustomsDataDto;
import com.msg.customs.adapter.in.web.model.MasterPartDto;
import com.msg.customs.adapter.in.web.model.TaskDto;
import com.msg.customs.domain.UiCustomsData;
import com.msg.customs.domain.UiMasterPart;
import com.msg.customs.domain.UiTask;
import com.msg.customs.model.CertifiedEnum;
import com.msg.customs.model.RequestStatusEnum;

public class UiMasterPartMapper {
	
	public static UiMasterPart toUiData(MasterPartDto dto) {
		UiMasterPart data = new UiMasterPart();
		
		data.setCatenaxId(dto.getCatenaxId());
		
		data.setCustomerPartNumber(dto.getCustomerPartNumber());
		data.setCustomerPartName(dto.getCustomerPartName());
		data.setManufacturerPartNumber(dto.getManufacturerPartNumber());
		data.setManufacturerPartName(dto.getManufacturerPartName());

		return data;
	}
	
	public static MasterPartDto toDto(UiMasterPart data) {
		MasterPartDto dto = new MasterPartDto();
		
		dto.setId(data.getId());
		dto.setCatenaxId(data.getCatenaxId());

		dto.setCustomerPartNumber(data.getCustomerPartNumber());
		dto.setCustomerPartName(data.getCustomerPartName());
		dto.setManufacturerPartNumber(data.getManufacturerPartNumber());
		dto.setManufacturerPartName(data.getManufacturerPartName());

		return dto;
	}

	public static UiTask toUiData(TaskDto dto) {
		UiTask data = new UiTask();

		data.setCustomerPartNumber(dto.getCustomerPartNumber());
		data.setCustomerPartName(dto.getCustomerPartName());
		data.setManufacturerPartNumber(dto.getManufacturerPartNumber());
		data.setManufacturerPartName(dto.getManufacturerPartName());

		return data;
	}

	public static TaskDto toDto(UiTask data) {
		TaskDto dto = new TaskDto();

		dto.setId(data.getId());

		dto.setCustomerPartNumber(data.getCustomerPartNumber());
		dto.setCustomerPartName(data.getCustomerPartName());
		dto.setManufacturerPartNumber(data.getManufacturerPartNumber());
		dto.setManufacturerPartName(data.getManufacturerPartName());

		return dto;
	}



	

}
