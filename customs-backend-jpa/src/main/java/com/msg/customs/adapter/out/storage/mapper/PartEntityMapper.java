package com.msg.customs.adapter.out.storage.mapper;

import com.msg.customs.adapter.out.storage.model.PartEntity;
import com.msg.customs.application.domain.model.Part;

/**
 * Mapper for part domain model and entity.
 */
public class PartEntityMapper {

	/**
	 * Maps a part domain model to an entity.
	 * @param part the part domain model
	 * @return the part entity
	 */
	public static PartEntity toEntity(Part part) {
		PartEntity entity = new PartEntity();
		entity.setName(part.getName());
		entity.setNumber(part.getNumber());
		return entity;
	}
	
	/**
	 * Maps a part entity to a domain model
	 * @param entity the part entity
	 * @return the part domain
	 */
	public static Part toDomain(PartEntity entity) {
		Part part = new Part();
		if (entity != null) {
			part.setName(entity.getName());
			part.setNumber(entity.getNumber());
		}
		return part;
	}
}
