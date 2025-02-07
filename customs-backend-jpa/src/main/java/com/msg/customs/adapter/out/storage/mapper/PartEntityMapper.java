package com.msg.customs.adapter.out.storage.mapper;

import com.msg.customs.adapter.out.storage.model.PartEntity;
import com.msg.customs.application.domain.model.Part;

public class PartEntityMapper {

	public static PartEntity toEntity(Part part) {
		PartEntity entity = new PartEntity();
		entity.setName(part.getName());
		entity.setNumber(part.getNumber());
		return entity;
	}
	
	public static Part toDomain(PartEntity entity) {
		Part part = new Part();
		if (entity == null) {
			part.setName(entity.getName());
			part.setNumber(entity.getNumber());
		}
		return part;
	}
}
