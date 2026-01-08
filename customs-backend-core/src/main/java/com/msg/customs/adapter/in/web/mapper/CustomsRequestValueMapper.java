package com.msg.customs.adapter.in.web.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.msg.customs.adapter.in.web.model.CreateRequestDto;
import com.msg.customs.adapter.in.web.model.CustomsRequestDto;
import com.msg.customs.adapter.in.web.model.NotificationHeaderDto;
import com.msg.customs.application.domain.model.CustomsRequest;
import com.msg.customs.application.domain.model.NotificationHeader;

/**
 * Mapper für CustomsRequest zwischen Domain und DTO.
 */
@Component
public class CustomsRequestValueMapper {

    /**
     * Domain zu DTO.
     */
    public CustomsRequestDto toDto(CustomsRequest request) {
        if (request == null) {
            return null;
        }

        CustomsRequestDto dto = new CustomsRequestDto();
        dto.setId(request.getId());
        dto.setHeader(toHeaderDto(request.getHeader()));
        dto.setCustomerPartId(request.getCustomerPartId());
        dto.setSupplierBpnl(request.getSupplierBpnl());
        dto.setSupplierName(request.getSupplierName());
        dto.setTradeRelation(request.getTradeRelation());
        dto.setRequestedValidFrom(request.getRequestedValidFrom());
        dto.setRequestedValidUntil(request.getRequestedValidUntil());
        dto.setStatus(request.getStatus());
        dto.setStatusDisplayName(request.getStatus() != null ? request.getStatus().getDisplayName() : null);
        dto.setDirection(request.getDirection());
        dto.setSentAt(request.getSentAt());
        dto.setAnsweredAt(request.getAnsweredAt());
        dto.setRejectionReason(request.getRejectionReason());
        dto.setRemarks(request.getRemarks());
        dto.setResponseNotificationId(request.getResponseNotificationId());
        dto.setCreatedAt(request.getCreatedAt());
        dto.setUpdatedAt(request.getUpdatedAt());
        dto.setOverdue(request.isOverdue());
        dto.setDaysUntilDeadline(request.getDaysUntilDeadline());
        dto.setRequiresAction(request.requiresAction());

        return dto;
    }

    /**
     * NotificationHeader Domain zu DTO.
     */
    public NotificationHeaderDto toHeaderDto(NotificationHeader header) {
        if (header == null) {
            return null;
        }

        NotificationHeaderDto dto = new NotificationHeaderDto();
        dto.setMessageId(header.getMessageId());
        dto.setContext(header.getContext());
        dto.setSentDateTime(header.getSentDateTime());
        dto.setSenderBpnl(header.getSenderBpnl());
        dto.setReceiverBpnl(header.getReceiverBpnl());
        dto.setExpectedResponseBy(header.getExpectedResponseBy());
        dto.setVersion(header.getVersion());

        return dto;
    }

    /**
     * CreateRequestDto zu Domain.
     */
    public CustomsRequest toDomain(CreateRequestDto dto, String senderBpnl) {
        if (dto == null) {
            return null;
        }

        CustomsRequest request = CustomsRequest.createOutgoing(
                senderBpnl,
                dto.getSupplierBpnl(),
                dto.getCustomerPartId()
        );

        request.setTradeRelation(dto.getTradeRelation());
        request.setRequestedValidFrom(dto.getRequestedValidFrom());
        request.setRequestedValidUntil(dto.getRequestedValidUntil());
        request.setRemarks(dto.getRemarks());

        // Response-Frist setzen falls angegeben
        if (dto.getResponseDays() != null && dto.getResponseDays() > 0) {
            request.getHeader().setExpectedResponseBy(
                    java.time.LocalDateTime.now().plusDays(dto.getResponseDays())
            );
        }

        return request;
    }

    /**
     * Liste von Domain zu DTOs.
     */
    public List<CustomsRequestDto> toDtoList(List<CustomsRequest> requests) {
        return requests.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * DTO zu NotificationHeader Domain.
     */
    public NotificationHeader toHeaderDomain(NotificationHeaderDto dto) {
        if (dto == null) {
            return null;
        }

        NotificationHeader header = new NotificationHeader();
        header.setMessageId(dto.getMessageId());
        header.setContext(dto.getContext());
        header.setSentDateTime(dto.getSentDateTime());
        header.setSenderBpnl(dto.getSenderBpnl());
        header.setReceiverBpnl(dto.getReceiverBpnl());
        header.setExpectedResponseBy(dto.getExpectedResponseBy());
        header.setVersion(dto.getVersion());

        return header;
    }
}
