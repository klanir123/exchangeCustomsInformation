package com.msg.customs.adapter.out.storage.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.msg.customs.adapter.out.storage.model.CustomsRequestEntity;
import com.msg.customs.adapter.out.storage.model.NotificationHeaderEntity;
import com.msg.customs.adapter.out.storage.model.SupplierEntity;
import com.msg.customs.application.domain.model.CustomsRequest;
import com.msg.customs.application.domain.model.NotificationHeader;

/**
 * Mapper zwischen CustomsRequest Domain-Modell und JPA Entity.
 */
public class CustomsRequestEntityMapper {

    private CustomsRequestEntityMapper() {
        // Utility class
    }

    /**
     * Domain zu Entity.
     */
    public static CustomsRequestEntity toEntity(CustomsRequest domain, SupplierEntity supplier) {
        if (domain == null) {
            return null;
        }

        CustomsRequestEntity entity = new CustomsRequestEntity();
        entity.setId(domain.getId());
        entity.setHeader(toHeaderEntity(domain.getHeader()));
        entity.setCustomerPartId(domain.getCustomerPartId());
        entity.setSupplier(supplier);
        entity.setTradeRelation(domain.getTradeRelation());
        entity.setRequestedValidFrom(domain.getRequestedValidFrom());
        entity.setRequestedValidUntil(domain.getRequestedValidUntil());
        entity.setStatus(domain.getStatus());
        entity.setDirection(domain.getDirection());
        entity.setSentAt(domain.getSentAt());
        entity.setAnsweredAt(domain.getAnsweredAt());
        entity.setRejectionReason(domain.getRejectionReason());
        entity.setRemarks(domain.getRemarks());
        entity.setResponseNotificationId(domain.getResponseNotificationId());

        return entity;
    }

    /**
     * Entity zu Domain.
     */
    public static CustomsRequest toDomain(CustomsRequestEntity entity) {
        if (entity == null) {
            return null;
        }

        CustomsRequest domain = new CustomsRequest();
        domain.setId(entity.getId());
        domain.setHeader(toHeaderDomain(entity.getHeader()));
        domain.setCustomerPartId(entity.getCustomerPartId());

        if (entity.getSupplier() != null) {
            domain.setSupplierBpnl(entity.getSupplier().getBpnl());
            domain.setSupplierName(entity.getSupplier().getName());
        }

        domain.setTradeRelation(entity.getTradeRelation());
        domain.setRequestedValidFrom(entity.getRequestedValidFrom());
        domain.setRequestedValidUntil(entity.getRequestedValidUntil());
        domain.setStatus(entity.getStatus());
        domain.setDirection(entity.getDirection());
        domain.setSentAt(entity.getSentAt());
        domain.setAnsweredAt(entity.getAnsweredAt());
        domain.setRejectionReason(entity.getRejectionReason());
        domain.setRemarks(entity.getRemarks());
        domain.setResponseNotificationId(entity.getResponseNotificationId());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());

        return domain;
    }

    /**
     * NotificationHeader Domain zu Entity.
     */
    public static NotificationHeaderEntity toHeaderEntity(NotificationHeader domain) {
        if (domain == null) {
            return null;
        }

        NotificationHeaderEntity entity = new NotificationHeaderEntity();
        entity.setMessageId(domain.getMessageId());
        entity.setContext(domain.getContext());
        entity.setSentDateTime(domain.getSentDateTime());
        entity.setSenderBpnl(domain.getSenderBpnl());
        entity.setReceiverBpnl(domain.getReceiverBpnl());
        entity.setExpectedResponseBy(domain.getExpectedResponseBy());
        entity.setVersion(domain.getVersion());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());

        return entity;
    }

    /**
     * NotificationHeader Entity zu Domain.
     */
    public static NotificationHeader toHeaderDomain(NotificationHeaderEntity entity) {
        if (entity == null) {
            return null;
        }

        NotificationHeader domain = new NotificationHeader();
        domain.setMessageId(entity.getMessageId());
        domain.setContext(entity.getContext());
        domain.setSentDateTime(entity.getSentDateTime());
        domain.setSenderBpnl(entity.getSenderBpnl());
        domain.setReceiverBpnl(entity.getReceiverBpnl());
        domain.setExpectedResponseBy(entity.getExpectedResponseBy());
        domain.setVersion(entity.getVersion());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());

        return domain;
    }

    /**
     * Liste von Entities zu Domain-Objekten.
     */
    public static List<CustomsRequest> toDomainList(List<CustomsRequestEntity> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }
        return entities.stream()
                .map(CustomsRequestEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Aktualisiert ein Entity mit Werten aus dem Domain-Objekt.
     */
    public static void updateEntity(CustomsRequestEntity entity, CustomsRequest domain) {
        if (entity == null || domain == null) {
            return;
        }

        entity.setCustomerPartId(domain.getCustomerPartId());
        entity.setTradeRelation(domain.getTradeRelation());
        entity.setRequestedValidFrom(domain.getRequestedValidFrom());
        entity.setRequestedValidUntil(domain.getRequestedValidUntil());
        entity.setStatus(domain.getStatus());
        entity.setSentAt(domain.getSentAt());
        entity.setAnsweredAt(domain.getAnsweredAt());
        entity.setRejectionReason(domain.getRejectionReason());
        entity.setRemarks(domain.getRemarks());
        entity.setResponseNotificationId(domain.getResponseNotificationId());

        // Header aktualisieren
        if (entity.getHeader() != null && domain.getHeader() != null) {
            entity.getHeader().setSentDateTime(domain.getHeader().getSentDateTime());
            entity.getHeader().setExpectedResponseBy(domain.getHeader().getExpectedResponseBy());
        }
    }
}
