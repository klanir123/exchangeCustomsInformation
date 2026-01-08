package com.msg.customs.adapter.in.web.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.msg.customs.adapter.in.web.model.CustomsNotificationDto;
import com.msg.customs.adapter.in.web.model.NotificationHeaderDto;
import com.msg.customs.adapter.in.web.model.NotificationItemDto;
import com.msg.customs.application.domain.model.CustomsNotification;
import com.msg.customs.application.domain.model.NotificationHeader;
import com.msg.customs.application.domain.model.NotificationItem;

/**
 * Mapper zwischen CustomsNotification Domain und DTO.
 */
@Component
public class CustomsNotificationValueMapper {

    // ==================== Notification Mapping ====================

    public CustomsNotificationDto toDto(CustomsNotification domain) {
        if (domain == null) return null;

        CustomsNotificationDto dto = new CustomsNotificationDto();
        dto.setId(domain.getId());
        dto.setHeader(headerToDto(domain.getHeader()));
        dto.setDirection(domain.getDirection());
        dto.setStatus(domain.getStatus());
        dto.setStatusDisplayName(domain.getStatus() != null ? domain.getStatus().getDisplayName() : null);
        dto.setSupplierBpnl(domain.getSupplierBpnl());
        dto.setSupplierName(domain.getSupplierName());
        dto.setOriginalRequestId(domain.getOriginalRequestId());
        dto.setRemarks(domain.getRemarks());
        dto.setCreatedAt(domain.getCreatedAt());
        dto.setUpdatedAt(domain.getUpdatedAt());

        // Berechnete Felder
        dto.setItemCount(domain.getItemCount());
        dto.setUnadoptedItemCount(domain.getUnadoptedItemCount());
        dto.setEditable(domain.isEditable());
        dto.setCanBeSent(domain.canBeSent());

        if (domain.getListOfItems() != null) {
            dto.setListOfItems(domain.getListOfItems().stream()
                    .map(this::itemToDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public CustomsNotification toDomain(CustomsNotificationDto dto) {
        if (dto == null) return null;

        CustomsNotification domain = new CustomsNotification();
        domain.setId(dto.getId());
        domain.setHeader(headerToDomain(dto.getHeader()));
        domain.setDirection(dto.getDirection());
        domain.setStatus(dto.getStatus());
        domain.setSupplierBpnl(dto.getSupplierBpnl());
        domain.setSupplierName(dto.getSupplierName());
        domain.setOriginalRequestId(dto.getOriginalRequestId());
        domain.setRemarks(dto.getRemarks());
        domain.setCreatedAt(dto.getCreatedAt());
        domain.setUpdatedAt(dto.getUpdatedAt());

        if (dto.getListOfItems() != null) {
            domain.setListOfItems(dto.getListOfItems().stream()
                    .map(this::itemToDomain)
                    .collect(Collectors.toList()));
        }

        return domain;
    }

    public List<CustomsNotificationDto> toDtoList(List<CustomsNotification> domains) {
        return domains.stream().map(this::toDto).collect(Collectors.toList());
    }

    // ==================== Header Mapping ====================

    public NotificationHeaderDto headerToDto(NotificationHeader domain) {
        if (domain == null) return null;

        NotificationHeaderDto dto = new NotificationHeaderDto();
        dto.setMessageId(domain.getMessageId());
        dto.setContext(domain.getContext());
        dto.setSentDateTime(domain.getSentDateTime());
        dto.setSenderBpnl(domain.getSenderBpnl());
        dto.setReceiverBpnl(domain.getReceiverBpnl());
        dto.setExpectedResponseBy(domain.getExpectedResponseBy());
        dto.setVersion(domain.getVersion());

        return dto;
    }

    public NotificationHeader headerToDomain(NotificationHeaderDto dto) {
        if (dto == null) return null;

        NotificationHeader domain = new NotificationHeader();
        domain.setMessageId(dto.getMessageId());
        domain.setContext(dto.getContext());
        domain.setSentDateTime(dto.getSentDateTime());
        domain.setSenderBpnl(dto.getSenderBpnl());
        domain.setReceiverBpnl(dto.getReceiverBpnl());
        domain.setExpectedResponseBy(dto.getExpectedResponseBy());
        domain.setVersion(dto.getVersion());

        return domain;
    }

    // ==================== Item Mapping ====================

    public NotificationItemDto itemToDto(NotificationItem domain) {
        if (domain == null) return null;

        NotificationItemDto dto = new NotificationItemDto();
        dto.setId(domain.getId());
        dto.setCatenaXId(domain.getCatenaXId());
        dto.setType(domain.getType());
        dto.setConfirmationDate(domain.getConfirmationDate());
        dto.setValidFrom(domain.getValidFrom());
        dto.setValidUntil(domain.getValidUntil());
        dto.setCommercialCountryOfOrigin(domain.getCommercialCountryOfOrigin());
        dto.setFederalState(domain.getFederalState());
        dto.setPreferredStatus(domain.getPreferredStatus());
        dto.setCompletionStatus(domain.getCompletionStatus());
        dto.setTracedWorthValue(domain.getTracedWorthValue());
        dto.setTracedWorthCurrency(domain.getTracedWorthCurrency());
        dto.setContactPerson(domain.getContactPerson());
        dto.setPhoneNumber(domain.getPhoneNumber());
        dto.setEmail(domain.getEmail());
        dto.setJobTitle(domain.getJobTitle());
        dto.setTradeRelations(domain.getTradeRelations());
        dto.setCustomerPartId(domain.getCustomerPartId());
        dto.setManufacturerPartId(domain.getManufacturerPartId());
        dto.setNameAtCustomer(domain.getNameAtCustomer());
        dto.setNameAtManufacturer(domain.getNameAtManufacturer());
        dto.setSourceLleId(domain.getSourceLleId());
        dto.setAdopted(domain.isAdopted());
        dto.setAdoptedLleId(domain.getAdoptedLleId());
        dto.setCreatedAt(domain.getCreatedAt());
        dto.setUpdatedAt(domain.getUpdatedAt());

        // Berechnete Felder
        dto.setCompleted(domain.isCompleted());
        dto.setPreferential(domain.isPreferential());

        return dto;
    }

    public NotificationItem itemToDomain(NotificationItemDto dto) {
        if (dto == null) return null;

        NotificationItem domain = new NotificationItem();
        domain.setId(dto.getId());
        domain.setCatenaXId(dto.getCatenaXId());
        domain.setType(dto.getType());
        domain.setConfirmationDate(dto.getConfirmationDate());
        domain.setValidFrom(dto.getValidFrom());
        domain.setValidUntil(dto.getValidUntil());
        domain.setCommercialCountryOfOrigin(dto.getCommercialCountryOfOrigin());
        domain.setFederalState(dto.getFederalState());
        domain.setPreferredStatus(dto.getPreferredStatus());
        domain.setCompletionStatus(dto.getCompletionStatus());
        domain.setTracedWorthValue(dto.getTracedWorthValue());
        domain.setTracedWorthCurrency(dto.getTracedWorthCurrency());
        domain.setContactPerson(dto.getContactPerson());
        domain.setPhoneNumber(dto.getPhoneNumber());
        domain.setEmail(dto.getEmail());
        domain.setJobTitle(dto.getJobTitle());
        domain.setTradeRelations(dto.getTradeRelations());
        domain.setCustomerPartId(dto.getCustomerPartId());
        domain.setManufacturerPartId(dto.getManufacturerPartId());
        domain.setNameAtCustomer(dto.getNameAtCustomer());
        domain.setNameAtManufacturer(dto.getNameAtManufacturer());
        domain.setSourceLleId(dto.getSourceLleId());
        domain.setAdopted(dto.isAdopted());
        domain.setAdoptedLleId(dto.getAdoptedLleId());
        domain.setCreatedAt(dto.getCreatedAt());
        domain.setUpdatedAt(dto.getUpdatedAt());

        return domain;
    }

    public List<NotificationItemDto> itemsToDtoList(List<NotificationItem> domains) {
        return domains.stream().map(this::itemToDto).collect(Collectors.toList());
    }
}
