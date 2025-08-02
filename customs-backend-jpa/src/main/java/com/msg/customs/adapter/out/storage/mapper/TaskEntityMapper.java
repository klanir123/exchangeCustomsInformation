package com.msg.customs.adapter.out.storage.mapper;

import com.msg.customs.adapter.out.storage.model.TaskEntity;
import com.msg.customs.application.domain.model.Task;

/**
 * Mapper for part domain model and entity.
 */
public class TaskEntityMapper {

	/**
	 * Maps a part domain model to an entity.
	 * @param task the part domain model
	 * @return the part entity
	 */
	public static TaskEntity toEntity(Task task) {
		TaskEntity entity = new TaskEntity();
		entity.setId(task.getId());
		entity.setCatenaXId(task.getCatenaXId());
		entity.setCustomerPartNumber(task.getCustomerPartNumber());
		entity.setCustomerPartName(task.getCustomerPartName());
		entity.setManufacturerPartName(task.getManufacturerPartName());
		entity.setManufacturerPartNumber(task.getManufacturerPartNumber());
		entity.setRequestDate(task.getRequestDate());
		return entity;
	}
	
	/**
	 * Maps a part entity to a domain model
	 * @param entity the part entity
	 * @return the part domain
	 */
	public static Task toDomain(TaskEntity entity) {
		Task task = new Task();
		if (entity != null) {
			task.setId(entity.getId());
			task.setCatenaXId(entity.getCatenaXId());
			task.setCustomerPartName(entity.getCustomerPartName());
			task.setCustomerPartNumber(entity.getCustomerPartNumber());
			task.setManufacturerPartName(entity.getManufacturerPartName());
			task.setManufacturerPartNumber(entity.getManufacturerPartNumber());
			task.setRequestDate(entity.getRequestDate());
		}
		return task;
	}
}
