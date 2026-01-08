package com.msg.customs.adapter.out.storage.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.msg.customs.adapter.out.storage.model.CustomsNotificationEntity;
import com.msg.customs.adapter.out.storage.model.NotificationHeaderEntity;
import com.msg.customs.adapter.out.storage.model.NotificationItemEntity;
import com.msg.customs.application.domain.model.CustomsNotification;
import com.msg.customs.application.domain.model.NotificationHeader;
import com.msg.customs.application.domain.model.NotificationItem;

/**
 * Mapper zwischen CustomsNotification Entity und Domain Model.
 */
@Component
public class CustomsNotificationEntityMapper {

    // ==================== Notification Mapping ====================

    public CustomsNotification toDomain(CustomsNotificationEntity entity) {
        if (entity == null) return null;

        CustomsNotification domain = new CustomsNotification();
        domain.setId(entity.getId());
        domain.setHeader(headerToDomain(entity.getHeader()));
        domain.setDirection(entity.getDirection());
        domain.setStatus(entity.getStatus());
        domain.setRemarks(entity.getRemarks());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());

        if (entity.getSupplier() != null) {
            domain.setSupplierBpnl(entity.getSupplier().getBpnl());
            domain.setSupplierName(entity.getSupplier().getName());
        }

        if (entity.getOriginalRequest() != null) {
            domain.setOriginalRequestId(entity.getOriginalRequest().getId());
        }

        if (entity.getListOfItems() != null) {
            domain.setListOfItems(entity.getListOfItems().stream()
                    .map(this::itemToDomain)
                    .collect(Collectors.toList()));
        }

        return domain;
    }

    public CustomsNotificationEntity toEntity(CustomsNotification domain) {
        if (domain == null) return null;

        CustomsNotificationEntity entity = new CustomsNotificationEntity();
        entity.setId(domain.getId());
        entity.setHeader(headerToEntity(domain.getHeader()));
        entity.setDirection(domain.getDirection());
        entity.setStatus(domain.getStatus());
        entity.setRemarks(domain.getRemarks());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());

        if (domain.getListOfItems() != null) {
            for (NotificationItem itemDomain : domain.getListOfItems()) {
                NotificationItemEntity itemEntity = itemToEntity(itemDomain);
                entity.addItem(itemEntity);
            }
        }

        return entity;
    }

    public List<CustomsNotification> toDomainList(List<CustomsNotificationEntity> entities) {
        return entities.stream().map(this::toDomain).collect(Collectors.toList());
    }

    // ==================== Header Mapping ====================

    public NotificationHeader headerToDomain(NotificationHeaderEntity entity) {
        if (entity == null) return null;

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

    public NotificationHeaderEntity headerToEntity(NotificationHeader domain) {
        if (domain == null) return null;

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

    // ==================== Item Mapping ====================

    public NotificationItem itemToDomain(NotificationItemEntity entity) {
        if (entity == null) return null;

        NotificationItem domain = new NotificationItem();
        domain.setId(entity.getId());
        domain.setCatenaXId(entity.getCatenaXId());
        domain.setType(entity.getType());
        domain.setConfirmationDate(entity.getConfirmationDate());
        domain.setValidFrom(entity.getValidFrom());
        domain.setValidUntil(entity.getValidUntil());
        domain.setCommercialCountryOfOrigin(entity.getCommercialCountryOfOrigin());
        domain.setFederalState(entity.getFederalState());
        domain.setPreferredStatus(entity.getPreferredStatus());
        domain.setCompletionStatus(entity.getCompletionStatus());
        domain.setTracedWorthValue(entity.getTracedWorthValue());
        domain.setTracedWorthCurrency(entity.getTracedWorthCurrency());
        domain.setContactPerson(entity.getContactPerson());
        domain.setPhoneNumber(entity.getPhoneNumber());
        domain.setEmail(entity.getEmail());
        domain.setJobTitle(entity.getJobTitle());
        domain.setTradeRelations(entity.getTradeRelations());
        domain.setCustomerPartId(entity.getCustomerPartId());
        domain.setManufacturerPartId(entity.getManufacturerPartId());
        domain.setNameAtCustomer(entity.getNameAtCustomer());
        domain.setNameAtManufacturer(entity.getNameAtManufacturer());
        domain.setAdopted(entity.isAdopted());
        domain.setAdoptedLleId(entity.getAdoptedLleId());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());

        if (entity.getSourceLle() != null) {
            domain.setSourceLleId(entity.getSourceLle().getId());
        }

        return domain;
    }

    public NotificationItemEntity itemToEntity(NotificationItem domain) {
        if (domain == null) return null;

        NotificationItemEntity entity = new NotificationItemEntity();
        entity.setId(domain.getId());
        entity.setCatenaXId(domain.getCatenaXId());
        entity.setType(domain.getType());
        entity.setConfirmationDate(domain.getConfirmationDate());
        entity.setValidFrom(domain.getValidFrom());
        entity.setValidUntil(domain.getValidUntil());
        entity.setCommercialCountryOfOrigin(domain.getCommercialCountryOfOrigin());
        entity.setFederalState(domain.getFederalState());
        entity.setPreferredStatus(domain.getPreferredStatus());
        entity.setCompletionStatus(domain.getCompletionStatus());
        entity.setTracedWorthValue(domain.getTracedWorthValue());
        entity.setTracedWorthCurrency(domain.getTracedWorthCurrency());
        entity.setContactPerson(domain.getContactPerson());
        entity.setPhoneNumber(domain.getPhoneNumber());
        entity.setEmail(domain.getEmail());
        entity.setJobTitle(domain.getJobTitle());
        entity.setTradeRelations(domain.getTradeRelations());
        entity.setCustomerPartId(domain.getCustomerPartId());
        entity.setManufacturerPartId(domain.getManufacturerPartId());
        entity.setNameAtCustomer(domain.getNameAtCustomer());
        entity.setNameAtManufacturer(domain.getNameAtManufacturer());
        entity.setAdopted(domain.isAdopted());
        entity.setAdoptedLleId(domain.getAdoptedLleId());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());

        return entity;
    }

    public List<NotificationItem> itemsToDomainList(List<NotificationItemEntity> entities) {
        return entities.stream().map(this::itemToDomain).collect(Collectors.toList());
    }
}
