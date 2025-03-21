package com.msg.customs.adapter.out.storage.mapper;

import com.msg.customs.adapter.out.storage.model.ContactEntity;
import com.msg.customs.application.domain.model.ContactData;

/**
 * Mapper for contact data domain model and entity.
 */
public class ContactDataEntityMapper {

	/**
	 * Maps the domain model {@link ContactData} to the entity representation.
	 * @param data contact data domain model
	 * @return contact entity
	 */
	public static ContactEntity toEntity(ContactData data) {
		ContactEntity entity = new ContactEntity();
		entity.setName(data.getName());
		entity.setEmail(data.getEmail());
		entity.setPhone(data.getPhone());
		entity.setJobTitle(data.getJobTitle());
		return entity;
	}

	/**
	 * Maps the entity representation to the domain model {@link ContactData}.
	 * @param entity contact entity
	 * @return contact data domain model
	 */
	public static ContactData toDomain(ContactEntity entity) {
		ContactData data = new ContactData();
		if (entity != null) {
			data.setName(entity.getName());
			data.setEmail(entity.getEmail());
			data.setPhone(entity.getPhone());
			data.setJobTitle(entity.getJobTitle());
		}
		return data;
	}

}
