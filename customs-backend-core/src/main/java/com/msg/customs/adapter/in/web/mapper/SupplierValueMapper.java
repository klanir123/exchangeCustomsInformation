package com.msg.customs.adapter.in.web.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.msg.customs.adapter.in.web.model.SupplierContactDto;
import com.msg.customs.adapter.in.web.model.SupplierDto;
import com.msg.customs.application.domain.model.Supplier;
import com.msg.customs.application.domain.model.SupplierContact;

/**
 * Mapper zwischen Supplier Domain-Modell und DTO.
 */
public class SupplierValueMapper {

	private SupplierValueMapper() {
		// Utility class
	}

	public static SupplierDto toDto(Supplier domain) {
		if (domain == null) {
			return null;
		}

		SupplierDto dto = new SupplierDto();
		dto.setBpnl(domain.getBpnl());
		dto.setName(domain.getName());
		dto.setStreet(domain.getStreet());
		dto.setPostalCode(domain.getPostalCode());
		dto.setCity(domain.getCity());
		dto.setCountry(domain.getCountry());
		dto.setStatus(domain.getStatus());
		dto.setCreatedAt(domain.getCreatedAt());
		dto.setUpdatedAt(domain.getUpdatedAt());

		if (domain.getContacts() != null) {
			dto.setContacts(domain.getContacts().stream()
				.map(SupplierValueMapper::toContactDto)
				.collect(Collectors.toList()));
		}

		return dto;
	}

	public static Supplier toDomain(SupplierDto dto) {
		if (dto == null) {
			return null;
		}

		Supplier domain = new Supplier();
		domain.setBpnl(dto.getBpnl());
		domain.setName(dto.getName());
		domain.setStreet(dto.getStreet());
		domain.setPostalCode(dto.getPostalCode());
		domain.setCity(dto.getCity());
		domain.setCountry(dto.getCountry());
		domain.setStatus(dto.getStatus());

		if (dto.getContacts() != null) {
			domain.setContacts(dto.getContacts().stream()
				.map(SupplierValueMapper::toContactDomain)
				.collect(Collectors.toList()));
		} else {
			domain.setContacts(new ArrayList<>());
		}

		return domain;
	}

	public static SupplierContactDto toContactDto(SupplierContact domain) {
		if (domain == null) {
			return null;
		}

		SupplierContactDto dto = new SupplierContactDto();
		dto.setId(domain.getId());
		dto.setContactPerson(domain.getContactPerson());
		dto.setPhoneNumber(domain.getPhoneNumber());
		dto.setEmail(domain.getEmail());
		dto.setJobTitle(domain.getJobTitle());
		dto.setPrimary(domain.isPrimary());
		dto.setPreferredForNotifications(domain.isPreferredForNotifications());

		return dto;
	}

	public static SupplierContact toContactDomain(SupplierContactDto dto) {
		if (dto == null) {
			return null;
		}

		SupplierContact domain = new SupplierContact();
		domain.setId(dto.getId());
		domain.setContactPerson(dto.getContactPerson());
		domain.setPhoneNumber(dto.getPhoneNumber());
		domain.setEmail(dto.getEmail());
		domain.setJobTitle(dto.getJobTitle());
		domain.setPrimary(dto.isPrimary());
		domain.setPreferredForNotifications(dto.isPreferredForNotifications());

		return domain;
	}

	public static List<SupplierDto> toDtoList(List<Supplier> domains) {
		if (domains == null) {
			return new ArrayList<>();
		}
		return domains.stream()
			.map(SupplierValueMapper::toDto)
			.collect(Collectors.toList());
	}
}
