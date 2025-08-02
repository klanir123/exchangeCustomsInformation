package com.msg.customs.mapper;

import com.msg.customs.adapter.in.web.model.TaskDto;
import com.msg.customs.domain.UiTask;

public class UiTaskMapper {
	
	public static UiTask toUiData(TaskDto dto) {
		UiTask data = new UiTask();
		data.setId(dto.getId());
		data.setCatenaxID(dto.getCatenaXId());
		data.setCustomerPartNumber(dto.getCustomerPartNumber());
		data.setCustomerPartName(dto.getCustomerPartName());
		data.setManufacturerPartNumber(dto.getManufacturerPartNumber());
		data.setManufacturerPartName(dto.getManufacturerPartName());
		data.setRequestDate(dto.getRequestDate());

		return data;
	}
	
	public static TaskDto toDto(UiTask data) {
		TaskDto dto = new TaskDto();
		
		dto.setId(data.getId());
		dto.setCatenaXId(data.getCatenaxID());
		dto.setCustomerPartNumber(data.getCustomerPartNumber());
		dto.setCustomerPartName(data.getCustomerPartName());
		dto.setManufacturerPartNumber(data.getManufacturerPartNumber());
		dto.setManufacturerPartName(data.getManufacturerPartName());
		dto.setRequestDate(data.getRequestDate());

		return dto;
	}

}
