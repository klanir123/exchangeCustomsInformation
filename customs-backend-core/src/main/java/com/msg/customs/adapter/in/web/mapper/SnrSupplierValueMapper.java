package com.msg.customs.adapter.in.web.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.msg.customs.adapter.in.web.model.SnrSupplierLinkDto;
import com.msg.customs.application.domain.model.SnrSupplierLink;

/**
 * Mapper zwischen SnrSupplierLink Domain-Modell und DTO.
 */
public class SnrSupplierValueMapper {

	private SnrSupplierValueMapper() {
		// Utility class
	}

	public static SnrSupplierLinkDto toDto(SnrSupplierLink domain) {
		if (domain == null) {
			return null;
		}

		SnrSupplierLinkDto dto = new SnrSupplierLinkDto();
		dto.setMasterpartId(domain.getMasterpartId());
		dto.setCatenaXId(domain.getCatenaXId());
		dto.setSupplierBpnl(domain.getSupplierBpnl());
		dto.setSupplierName(domain.getSupplierName());
		dto.setPrimary(domain.isPrimary());
		dto.setNote(domain.getNote());

		return dto;
	}

	public static SnrSupplierLink toDomain(SnrSupplierLinkDto dto) {
		if (dto == null) {
			return null;
		}

		SnrSupplierLink domain = new SnrSupplierLink();
		domain.setMasterpartId(dto.getMasterpartId());
		domain.setCatenaXId(dto.getCatenaXId());
		domain.setSupplierBpnl(dto.getSupplierBpnl());
		domain.setSupplierName(dto.getSupplierName());
		domain.setPrimary(dto.isPrimary());
		domain.setNote(dto.getNote());

		return domain;
	}

	public static List<SnrSupplierLinkDto> toDtoList(List<SnrSupplierLink> domains) {
		if (domains == null) {
			return new ArrayList<>();
		}
		return domains.stream()
			.map(SnrSupplierValueMapper::toDto)
			.collect(Collectors.toList());
	}
}
