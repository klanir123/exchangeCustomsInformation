package com.msg.customs.adapter.out.storage.mapper;

import com.msg.customs.adapter.out.storage.model.ContactEntity;
import com.msg.customs.application.domain.model.ContactData;

public class ContactDataEntityMapper {


	public static ContactEntity toEntity(ContactData data) {
		ContactEntity entity = new ContactEntity();
		entity.setName(data.getName());
		entity.setEmail(data.getEmail());
		entity.setPhone(data.getPhone());
		entity.setJobTitle(data.getJobTitle());
		return entity;		
	}

	public static ContactData toDomain(ContactEntity entity) {
		ContactData data = new ContactData();
		data.setName(entity.getName());
		data.setEmail(entity.getEmail());
		data.setPhone(entity.getPhone());
		data.setJobTitle(entity.getJobTitle());
		return data;
	}

}
