package com.msg.customs.adapter.in.web.mapper;

import com.msg.customs.adapter.in.web.model.TaskDto;
import com.msg.customs.application.domain.model.Task;

/**
 * Mapper to map the Dto to the internal customs data representation.
 */
public class TaskValueMapper {

	/**
	 * Maps the internal customs data to the Dto.
	 * @param data the customs data
	 * @return the dto.	
	 */
    public static TaskDto toDto(Task data) {
        TaskDto dto = new TaskDto();
        dto.setCatenaXId(data.getCatenaXId());
        dto.setCustomerPartNumber(data.getCustomerPartNumber());
        dto.setCustomerPartName(data.getCustomerPartName());
        dto.setManufacturerPartNumber(data.getManufacturerPartNumber());
        dto.setManufacturerPartName(data.getManufacturerPartName());
        dto.setRequestDate(data.getRequestDate());
        return dto;
    }

	/**
	 * Maps the Dto to the internal task data.
	 * 
	 * @param dto the dto
	 * @return the task data.
	 */
    public static Task toDomain(TaskDto dto) {
        Task data = new Task();

        data.setCatenaXId(dto.getCatenaXId());
        data.setCustomerPartNumber(dto.getCustomerPartNumber());
        data.setCustomerPartName(dto.getCustomerPartName());
        data.setManufacturerPartNumber(dto.getManufacturerPartNumber());
        data.setManufacturerPartName(dto.getManufacturerPartName());

        return data;
    }
}