package com.msg.customs.adapter.out.storage.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.msg.customs.adapter.out.storage.model.SnrSupplierEntity;
import com.msg.customs.application.domain.model.SnrSupplierLink;

/**
 * Mapper zwischen SnrSupplierLink Domain-Modell und JPA Entity.
 */
public class SnrSupplierEntityMapper {

	private SnrSupplierEntityMapper() {
		// Utility class
	}

	public static SnrSupplierLink toDomain(SnrSupplierEntity entity) {
		if (entity == null) {
			return null;
		}

		SnrSupplierLink domain = new SnrSupplierLink();
		domain.setMasterpartId(entity.getMasterpart().getId());
		domain.setCatenaXId(entity.getMasterpart().getCatenaXId());
		domain.setSupplierBpnl(entity.getSupplier().getBpnl());
		domain.setSupplierName(entity.getSupplier().getName());
		domain.setPrimary(entity.isPrimary());
		domain.setNote(entity.getNote());

		return domain;
	}

	public static List<SnrSupplierLink> toDomainList(List<SnrSupplierEntity> entities) {
		if (entities == null) {
			return new ArrayList<>();
		}
		return entities.stream()
			.map(SnrSupplierEntityMapper::toDomain)
			.collect(Collectors.toList());
	}
}
