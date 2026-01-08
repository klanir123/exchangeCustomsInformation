package com.msg.customs.adapter.in.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.msg.customs.adapter.in.web.RequestAPI;
import com.msg.customs.adapter.in.web.mapper.CustomsRequestValueMapper;
import com.msg.customs.adapter.in.web.model.CreateRequestDto;
import com.msg.customs.adapter.in.web.model.CustomsRequestDto;
import com.msg.customs.adapter.in.web.model.ReceiveRequestDto;
import com.msg.customs.application.domain.model.CustomsRequest;
import com.msg.customs.application.domain.model.NotificationHeader;
import com.msg.customs.application.domain.service.CustomsRequestService;
import com.msg.customs.model.RequestStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller für RequestCustomsInformation (Epic 5).
 *
 * User Stories:
 * - 5.3: API-Endpunkte für Request-Verwaltung
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class RequestAPIController implements RequestAPI {

    private final CustomsRequestService requestService;
    private final CustomsRequestValueMapper mapper;

    // ==================== Allgemeine Endpunkte ====================

    @Override
    public ResponseEntity<List<CustomsRequestDto>> getAllRequests() {
        log.debug("GET /requests - Getting all requests");
        List<CustomsRequest> requests = requestService.findAll();
        return ResponseEntity.ok(mapper.toDtoList(requests));
    }

    @Override
    public ResponseEntity<CustomsRequestDto> getRequestById(Long id) {
        log.debug("GET /requests/{} - Getting request by ID", id);
        CustomsRequest request = requestService.findById(id);
        return ResponseEntity.ok(mapper.toDto(request));
    }

    @Override
    public ResponseEntity<Void> deleteRequest(Long id) {
        log.debug("DELETE /requests/{} - Deleting request", id);
        requestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== Ausgehende Anfragen ====================

    @Override
    public ResponseEntity<CustomsRequestDto> createRequest(CreateRequestDto dto) {
        log.debug("POST /requests - Creating new request for supplier {}", dto.getSupplierBpnl());
        CustomsRequest request = requestService.createRequest(
                dto.getSupplierBpnl(),
                dto.getCustomerPartId(),
                dto.getTradeRelation(),
                dto.getResponseDays() != null ? dto.getResponseDays() : 14,
                dto.getRemarks()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(request));
    }

    @Override
    public ResponseEntity<CustomsRequestDto> updateRequest(Long id, CreateRequestDto dto) {
        log.debug("PUT /requests/{} - Updating request", id);
        CustomsRequest request = requestService.updateRequest(
                id,
                dto.getCustomerPartId(),
                dto.getTradeRelation(),
                dto.getRequestedValidFrom(),
                dto.getRequestedValidUntil(),
                dto.getRemarks()
        );
        return ResponseEntity.ok(mapper.toDto(request));
    }

    @Override
    public ResponseEntity<CustomsRequestDto> sendRequest(Long id) {
        log.debug("POST /requests/{}/send - Sending request", id);
        CustomsRequest request = requestService.sendRequest(id);
        return ResponseEntity.ok(mapper.toDto(request));
    }

    @Override
    public ResponseEntity<List<CustomsRequestDto>> getOutgoingRequests() {
        log.debug("GET /requests/outgoing - Getting outgoing requests");
        List<CustomsRequest> requests = requestService.findOutgoingRequests();
        return ResponseEntity.ok(mapper.toDtoList(requests));
    }

    @Override
    public ResponseEntity<List<CustomsRequestDto>> getOutgoingRequestsByStatus(RequestStatus status) {
        log.debug("GET /requests/outgoing/by-status/{} - Getting outgoing requests by status", status);
        List<CustomsRequest> requests = requestService.findOutgoingRequestsByStatus(status);
        return ResponseEntity.ok(mapper.toDtoList(requests));
    }

    // ==================== Eingehende Anfragen ====================

    @Override
    public ResponseEntity<List<CustomsRequestDto>> getIncomingRequests() {
        log.debug("GET /requests/incoming - Getting incoming requests");
        List<CustomsRequest> requests = requestService.findIncomingRequests();
        return ResponseEntity.ok(mapper.toDtoList(requests));
    }

    @Override
    public ResponseEntity<List<CustomsRequestDto>> getIncomingRequestsByStatus(RequestStatus status) {
        log.debug("GET /requests/incoming/by-status/{} - Getting incoming requests by status", status);
        List<CustomsRequest> requests = requestService.findIncomingRequestsByStatus(status);
        return ResponseEntity.ok(mapper.toDtoList(requests));
    }

    @Override
    public ResponseEntity<CustomsRequestDto> receiveRequest(ReceiveRequestDto dto) {
        log.debug("POST /requests/receive - Receiving incoming request from {}", dto.getSenderBpnl());

        // NotificationHeader erstellen
        NotificationHeader header = NotificationHeader.createNew(
                dto.getSenderBpnl(),
                "BPNL000000000SUP" // Eigene BPNL als Empfänger
        );
        if (dto.getExpectedResponseBy() != null) {
            header.setExpectedResponseBy(dto.getExpectedResponseBy());
        }

        CustomsRequest request = requestService.receiveRequest(
                header,
                dto.getCustomerPartId(),
                dto.getTradeRelation(),
                dto.getRemarks()
        );

        // Gültigkeitsdaten setzen falls vorhanden
        if (dto.getRequestedValidFrom() != null) {
            request.setRequestedValidFrom(dto.getRequestedValidFrom());
        }
        if (dto.getRequestedValidUntil() != null) {
            request.setRequestedValidUntil(dto.getRequestedValidUntil());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(request));
    }

    @Override
    public ResponseEntity<CustomsRequestDto> acceptRequest(Long id) {
        log.debug("POST /requests/{}/accept - Accepting request", id);
        CustomsRequest request = requestService.acceptRequest(id);
        return ResponseEntity.ok(mapper.toDto(request));
    }

    @Override
    public ResponseEntity<CustomsRequestDto> answerRequest(Long id, List<Long> lleIds) {
        log.debug("POST /requests/{}/answer - Answering request with {} LLEs", id, lleIds.size());
        CustomsRequest request = requestService.answerRequest(id, lleIds);
        return ResponseEntity.ok(mapper.toDto(request));
    }

    @Override
    public ResponseEntity<CustomsRequestDto> rejectRequest(Long id, String reason) {
        log.debug("POST /requests/{}/reject - Rejecting request", id);
        CustomsRequest request = requestService.rejectRequest(id, reason);
        return ResponseEntity.ok(mapper.toDto(request));
    }

    // ==================== Status & Statistiken ====================

    @Override
    public ResponseEntity<List<CustomsRequestDto>> getOverdueRequests() {
        log.debug("GET /requests/overdue - Getting overdue requests");
        List<CustomsRequest> requests = requestService.findOverdueRequests();
        return ResponseEntity.ok(mapper.toDtoList(requests));
    }

    @Override
    public ResponseEntity<CustomsRequestDto> updateRequestStatus(Long id, RequestStatus status) {
        log.debug("PATCH /requests/{}/status - Updating status to {}", id, status);
        CustomsRequest request = requestService.updateStatus(id, status);
        return ResponseEntity.ok(mapper.toDto(request));
    }

    @Override
    public ResponseEntity<RequestStatsDto> getRequestStats() {
        log.debug("GET /requests/stats - Getting request statistics");
        CustomsRequestService.RequestStats stats = requestService.getStats();
        return ResponseEntity.ok(new RequestStatsDto(
                stats.outgoingTotal(),
                stats.outgoingOpen(),
                stats.outgoingAnswered(),
                stats.outgoingOverdue(),
                stats.incomingTotal(),
                stats.incomingOpen(),
                stats.incomingRequiresAction()
        ));
    }
}
