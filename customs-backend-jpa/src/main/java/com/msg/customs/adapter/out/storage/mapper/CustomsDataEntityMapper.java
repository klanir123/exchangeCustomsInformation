package com.msg.customs.adapter.out.storage.mapper;

import java.util.ArrayList;

import com.msg.customs.adapter.out.storage.model.CustomsEntity;
import com.msg.customs.adapter.out.storage.model.SupplierEntity;
import com.msg.customs.adapter.out.storage.repository.SupplierRepository;
import com.msg.customs.application.domain.model.CustomsData;

/**
 * Mapper for customs data domain model and entity.
 * Erweitert für Phase 3: LLE-Erweiterungen.
 */
public class CustomsDataEntityMapper {

	/**
	 * Maps the customs data domain model data to the entity.
	 * @param data the customs data domain model
	 * @return the customs data entity
	 */
    public static CustomsEntity toEntity(CustomsData data) {
        CustomsEntity entity = new CustomsEntity();

        entity.setId(data.getId());

        entity.setCatenaxId(data.getCatenaxId());
        entity.setRequestStatus(data.getRequestStatus());

        entity.setCustomerPart(PartEntityMapper.toEntity(data.getCustomerPart()));
        entity.setManufacturePart(PartEntityMapper.toEntity(data.getManufacturerPart()));

        entity.setType(data.getType());
        entity.setConfirmationDate(data.getConfirmationDate());
        entity.setValidFrom(data.getValidFrom());

        entity.setContact(ContactDataEntityMapper.toEntity(data.getContact()));

        entity.setCountry(data.getCountry());
        entity.setFederalState(data.getFederalState());
        // Neue Liste erstellen um shared references zu vermeiden
        entity.setOriginInformation(data.getOriginInformation() != null
            ? new ArrayList<>(data.getOriginInformation())
            : new ArrayList<>());
        entity.setPreferredStatus(data.getPreferredStatus());

        entity.setTracedWorth(TracedWorthEntityMapper.toEntity(data.getTracedWorth()));

        // Phase 3: LLE-Erweiterungen
        entity.setValidUntil(data.getValidUntil());
        entity.setLleStatus(data.getLleStatus());
        // Note: supplier und predecessor werden separat gesetzt (benötigen Repository-Zugriff)

        return entity;
    }

    /**
     * Maps the customs data domain model data to the entity with supplier lookup.
     * @param data the customs data domain model
     * @param supplierRepository das Repository für Lieferanten-Lookup
     * @return the customs data entity
     */
    public static CustomsEntity toEntity(CustomsData data, SupplierRepository supplierRepository) {
        CustomsEntity entity = toEntity(data);

        // Supplier setzen wenn vorhanden
        if (data.getSupplierBpnl() != null && supplierRepository != null) {
            supplierRepository.findById(data.getSupplierBpnl())
                .ifPresent(entity::setSupplier);
        }

        return entity;
    }

    /**
     * Maps the customs data entity to the domain model.
     * @param entity the customs data entity
     * @return the customs data domain model
     */
    public static CustomsData toDomain(CustomsEntity entity) {
        CustomsData data = new CustomsData();

        data.setId(entity.getId());

        data.setCatenaxId(entity.getCatenaxId());
        data.setRequestStatus(entity.getRequestStatus());

        data.setCustomerPart(PartEntityMapper.toDomain(entity.getCustomerPart()));
        data.setManufacturerPart(PartEntityMapper.toDomain(entity.getManufacturePart()));

        data.setType(entity.getType());
        data.setConfirmationDate(entity.getConfirmationDate());
        data.setValidFrom(entity.getValidFrom());

        data.setContact(ContactDataEntityMapper.toDomain(entity.getContact()));

        data.setCountry(entity.getCountry());
        data.setFederalState(entity.getFederalState());
        data.setOriginInformation(entity.getOriginInformation());
        data.setPreferredStatus(entity.getPreferredStatus());

        data.setTracedWorth(TracedWorthEntityMapper.toDomain(entity.getTracedWorth()));

        // Phase 3: LLE-Erweiterungen
        data.setValidUntil(entity.getValidUntil());
        data.setLleStatus(entity.getLleStatus());
        data.setPdfFileName(entity.getPdfFileName());
        data.setPdfUploadedAt(entity.getPdfUploadedAt());
        data.setHasPdf(entity.getPdfDocument() != null && entity.getPdfDocument().length > 0);

        // Supplier-Daten
        SupplierEntity supplier = entity.getSupplier();
        if (supplier != null) {
            data.setSupplierBpnl(supplier.getBpnl());
            data.setSupplierName(supplier.getName());
        }

        // Predecessor-ID
        if (entity.getPredecessor() != null) {
            data.setPredecessorId(entity.getPredecessor().getId());
        }

        return data;
    }
}
