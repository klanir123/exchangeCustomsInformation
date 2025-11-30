package com.msg.customs.adapter.out.storage.mapper;

import com.msg.customs.adapter.out.storage.model.MasterPartEntity;
import com.msg.customs.adapter.out.storage.model.PartEntity;
import com.msg.customs.application.domain.model.MasterPart;
import com.msg.customs.application.domain.model.Part;

/**
 * Mapper for part domain model and entity.
 */
public class MasterPartEntityMapper {

	/**
	 * Maps a part domain model to an entity.
	 * @param part the part domain model
	 * @return the part entity
	 */
	public static MasterPartEntity toEntity(MasterPart part) {
		MasterPartEntity entity = new MasterPartEntity();
		entity.setId(part.getId());
		entity.setCatenaXId(part.getCatenaxId());
		entity.setCustomerPartNumber(part.getCustomerPartNumber());
		entity.setCustomerPartName(part.getCustomerPartName());
		entity.setManufacturerPartName(part.getManufacturerPartName());
		entity.setManufacturerPartNumber(part.getManufacturerPartNumber());
		return entity;
	}
	
	/**
	 * Maps a part entity to a domain model
	 * @param entity the part entity
	 * @return the part domain
	 */
	public static MasterPart toDomain(MasterPartEntity entity) {
		MasterPart part = new MasterPart();
		if (entity != null) {
			part.setId(entity.getId());
			part.setCatenaxId(entity.getCatenaXId());
			part.setCustomerPartName(entity.getCustomerPartName());
			part.setCustomerPartNumber(entity.getCustomerPartNumber());
			part.setManufacturerPartName(entity.getManufacturerPartName());
			part.setManufacturerPartNumber(entity.getManufacturerPartNumber());
		}
		return part;
	}
}
