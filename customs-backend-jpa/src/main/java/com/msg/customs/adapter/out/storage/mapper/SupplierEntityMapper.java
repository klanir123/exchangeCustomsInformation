package com.msg.customs.adapter.out.storage.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.msg.customs.adapter.out.storage.model.SupplierContactEntity;
import com.msg.customs.adapter.out.storage.model.SupplierEntity;
import com.msg.customs.application.domain.model.Supplier;
import com.msg.customs.application.domain.model.SupplierContact;

/**
 * Mapper zwischen Supplier Domain-Modell und JPA Entity.
 */
public class SupplierEntityMapper {

	private SupplierEntityMapper() {
		// Utility class
	}

	public static SupplierEntity toEntity(Supplier domain) {
		if (domain == null) {
			return null;
		}

		SupplierEntity entity = new SupplierEntity();
		entity.setBpnl(domain.getBpnl());
		entity.setName(domain.getName());
		entity.setStreet(domain.getStreet());
		entity.setPostalCode(domain.getPostalCode());
		entity.setCity(domain.getCity());
		entity.setCountry(domain.getCountry());
		entity.setStatus(domain.getStatus());

		if (domain.getContacts() != null) {
			for (SupplierContact contact : domain.getContacts()) {
				SupplierContactEntity contactEntity = toContactEntity(contact);
				entity.addContact(contactEntity);
			}
		}

		return entity;
	}

	public static Supplier toDomain(SupplierEntity entity) {
		if (entity == null) {
			return null;
		}

		Supplier domain = new Supplier();
		domain.setBpnl(entity.getBpnl());
		domain.setName(entity.getName());
		domain.setStreet(entity.getStreet());
		domain.setPostalCode(entity.getPostalCode());
		domain.setCity(entity.getCity());
		domain.setCountry(entity.getCountry());
		domain.setStatus(entity.getStatus());
		domain.setCreatedAt(entity.getCreatedAt());
		domain.setUpdatedAt(entity.getUpdatedAt());

		if (entity.getContacts() != null) {
			domain.setContacts(entity.getContacts().stream()
				.map(SupplierEntityMapper::toContactDomain)
				.collect(Collectors.toList()));
		} else {
			domain.setContacts(new ArrayList<>());
		}

		return domain;
	}

	public static SupplierContactEntity toContactEntity(SupplierContact domain) {
		if (domain == null) {
			return null;
		}

		SupplierContactEntity entity = new SupplierContactEntity();
		entity.setId(domain.getId());
		entity.setContactPerson(domain.getContactPerson());
		entity.setPhoneNumber(domain.getPhoneNumber());
		entity.setEmail(domain.getEmail());
		entity.setJobTitle(domain.getJobTitle());
		entity.setPrimary(domain.isPrimary());
		entity.setPreferredForNotifications(domain.isPreferredForNotifications());

		return entity;
	}

	public static SupplierContact toContactDomain(SupplierContactEntity entity) {
		if (entity == null) {
			return null;
		}

		SupplierContact domain = new SupplierContact();
		domain.setId(entity.getId());
		domain.setContactPerson(entity.getContactPerson());
		domain.setPhoneNumber(entity.getPhoneNumber());
		domain.setEmail(entity.getEmail());
		domain.setJobTitle(entity.getJobTitle());
		domain.setPrimary(entity.isPrimary());
		domain.setPreferredForNotifications(entity.isPreferredForNotifications());

		return domain;
	}

	public static List<Supplier> toDomainList(List<SupplierEntity> entities) {
		if (entities == null) {
			return new ArrayList<>();
		}
		return entities.stream()
			.map(SupplierEntityMapper::toDomain)
			.collect(Collectors.toList());
	}
}
