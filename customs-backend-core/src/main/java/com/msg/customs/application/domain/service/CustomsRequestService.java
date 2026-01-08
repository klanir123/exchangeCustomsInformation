package com.msg.customs.application.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.msg.customs.application.domain.model.CustomsRequest;
import com.msg.customs.application.domain.model.NotificationHeader;
import com.msg.customs.application.port.out.CustomsRequestStorageAdapter;
import com.msg.customs.model.NotificationDirection;
import com.msg.customs.model.RequestStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service für CustomsRequest (RequestCustomsInformation).
 * Implementiert die Geschäftslogik für Zoll-Anfragen.
 *
 * User Stories:
 * - 5.1: Request Entity
 * - 5.3: API-Endpunkte
 * - 5.5: Überfälligkeits-Prüfung
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomsRequestService {

    private final CustomsRequestStorageAdapter requestStorage;

    // Eigene BPNL - kann über Konfiguration gesetzt werden
    private String ownBpnl = "BPNL000000000OEM";

    public void setOwnBpnl(String ownBpnl) {
        this.ownBpnl = ownBpnl;
    }

    // ==================== Allgemeine Operationen ====================

    /**
     * Findet alle Anfragen.
     */
    public List<CustomsRequest> findAll() {
        return requestStorage.findAll();
    }

    /**
     * Findet eine Anfrage nach ID.
     */
    public CustomsRequest findById(Long id) {
        return requestStorage.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Request not found: " + id));
    }

    /**
     * Löscht eine Anfrage (nur im DRAFT-Status).
     */
    public void deleteRequest(Long id) {
        CustomsRequest request = findById(id);
        if (request.getStatus() != RequestStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT requests can be deleted");
        }
        requestStorage.deleteById(id);
        log.info("Request deleted: {}", id);
    }

    // ==================== Ausgehende Anfragen ====================

    /**
     * Erstellt eine neue ausgehende Anfrage.
     */
    public CustomsRequest createRequest(String supplierBpnl, String customerPartId,
            String tradeRelation, int responseDays, String remarks) {
        log.info("Creating new request for supplier {} and part {}", supplierBpnl, customerPartId);

        CustomsRequest request = CustomsRequest.createOutgoing(ownBpnl, supplierBpnl, customerPartId);
        request.setTradeRelation(tradeRelation);
        request.setRemarks(remarks);
        request.getHeader().setExpectedResponseBy(LocalDateTime.now().plusDays(responseDays));

        CustomsRequest saved = requestStorage.save(request);
        log.info("Request created with ID: {}", saved.getId());

        return saved;
    }

    /**
     * Aktualisiert eine Anfrage (nur im DRAFT-Status).
     */
    public CustomsRequest updateRequest(Long id, String customerPartId, String tradeRelation,
            java.time.LocalDate requestedValidFrom, java.time.LocalDate requestedValidUntil,
            String remarks) {
        CustomsRequest request = findById(id);

        if (request.getStatus() != RequestStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT requests can be updated");
        }

        request.setCustomerPartId(customerPartId);
        request.setTradeRelation(tradeRelation);
        request.setRequestedValidFrom(requestedValidFrom);
        request.setRequestedValidUntil(requestedValidUntil);
        request.setRemarks(remarks);

        return requestStorage.save(request);
    }

    /**
     * Versendet eine Anfrage.
     */
    public CustomsRequest sendRequest(Long id) {
        CustomsRequest request = findById(id);

        if (request.getStatus() != RequestStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT requests can be sent");
        }

        request.setStatus(RequestStatus.SENT);
        request.setSentAt(LocalDateTime.now());
        request.getHeader().setSentDateTime(request.getSentAt());

        CustomsRequest saved = requestStorage.save(request);
        log.info("Request sent: {}", id);

        // TODO: EDC-Integration - Anfrage über EDC versenden
        // edcService.sendRequest(saved);

        return saved;
    }

    /**
     * Findet alle ausgehenden Anfragen.
     */
    public List<CustomsRequest> findOutgoingRequests() {
        return requestStorage.findByDirection(NotificationDirection.OUTGOING);
    }

    /**
     * Findet ausgehende Anfragen nach Status.
     */
    public List<CustomsRequest> findOutgoingRequestsByStatus(RequestStatus status) {
        return requestStorage.findByStatusAndDirection(status, NotificationDirection.OUTGOING);
    }

    // ==================== Eingehende Anfragen ====================

    /**
     * Findet alle eingehenden Anfragen.
     */
    public List<CustomsRequest> findIncomingRequests() {
        return requestStorage.findByDirection(NotificationDirection.INCOMING);
    }

    /**
     * Findet eingehende Anfragen nach Status.
     */
    public List<CustomsRequest> findIncomingRequestsByStatus(RequestStatus status) {
        return requestStorage.findByStatusAndDirection(status, NotificationDirection.INCOMING);
    }

    /**
     * Nimmt eine eingehende Anfrage in Bearbeitung.
     */
    public CustomsRequest acceptRequest(Long id) {
        CustomsRequest request = findById(id);

        if (request.getDirection() != NotificationDirection.INCOMING) {
            throw new IllegalStateException("Only incoming requests can be accepted");
        }
        if (request.getStatus() != RequestStatus.RECEIVED) {
            throw new IllegalStateException("Only RECEIVED requests can be accepted");
        }

        request.setStatus(RequestStatus.IN_PROGRESS);

        CustomsRequest saved = requestStorage.save(request);
        log.info("Request accepted: {}", id);

        return saved;
    }

    /**
     * Beantwortet eine Anfrage mit LLE-Daten.
     */
    public CustomsRequest answerRequest(Long id, List<Long> lleIds) {
        CustomsRequest request = findById(id);

        if (request.getDirection() != NotificationDirection.INCOMING) {
            throw new IllegalStateException("Only incoming requests can be answered");
        }
        if (!request.getStatus().requiresAction()) {
            throw new IllegalStateException("Request cannot be answered in current status: " + request.getStatus());
        }

        // TODO: CustomsNotification erstellen und versenden
        // Long notificationId = notificationService.createAndSendNotification(
        //     request.getHeader().getSenderBpnl(), lleIds, request.getId());
        // request.setResponseNotificationId(notificationId);

        request.setStatus(RequestStatus.ANSWERED);
        request.setAnsweredAt(LocalDateTime.now());

        CustomsRequest saved = requestStorage.save(request);
        log.info("Request answered: {}", id);

        return saved;
    }

    /**
     * Lehnt eine Anfrage ab.
     */
    public CustomsRequest rejectRequest(Long id, String reason) {
        CustomsRequest request = findById(id);

        if (request.getDirection() != NotificationDirection.INCOMING) {
            throw new IllegalStateException("Only incoming requests can be rejected");
        }
        if (!request.getStatus().requiresAction()) {
            throw new IllegalStateException("Request cannot be rejected in current status: " + request.getStatus());
        }

        request.setStatus(RequestStatus.REJECTED);
        request.setAnsweredAt(LocalDateTime.now());
        request.setRejectionReason(reason);

        CustomsRequest saved = requestStorage.save(request);
        log.info("Request rejected: {} - Reason: {}", id, reason);

        return saved;
    }

    /**
     * Empfängt eine eingehende Anfrage (von EDC).
     */
    public CustomsRequest receiveRequest(NotificationHeader header, String customerPartId,
            String tradeRelation, String remarks) {
        log.info("Receiving incoming request from {}", header.getSenderBpnl());

        CustomsRequest request = new CustomsRequest();
        request.setHeader(header);
        request.setCustomerPartId(customerPartId);
        request.setSupplierBpnl(ownBpnl); // Wir sind der Empfänger
        request.setTradeRelation(tradeRelation);
        request.setRemarks(remarks);
        request.setStatus(RequestStatus.RECEIVED);
        request.setDirection(NotificationDirection.INCOMING);
        request.setCreatedAt(LocalDateTime.now());

        CustomsRequest saved = requestStorage.save(request);
        log.info("Incoming request received with ID: {}", saved.getId());

        return saved;
    }

    // ==================== Status & Statistiken ====================

    /**
     * Findet überfällige Anfragen.
     */
    public List<CustomsRequest> findOverdueRequests() {
        return requestStorage.findOverdueRequests();
    }

    /**
     * Aktualisiert den Status einer Anfrage (Admin-Funktion).
     */
    public CustomsRequest updateStatus(Long id, RequestStatus newStatus) {
        CustomsRequest request = findById(id);
        request.setStatus(newStatus);
        return requestStorage.save(request);
    }

    /**
     * Prüft und markiert überfällige Anfragen.
     * Sollte periodisch aufgerufen werden (z.B. über Scheduler).
     */
    public void checkOverdueRequests() {
        log.info("Checking for overdue requests...");
        List<CustomsRequest> overdueRequests = findOverdueRequests();

        int count = 0;
        for (CustomsRequest request : overdueRequests) {
            if (request.getStatus() != RequestStatus.OVERDUE) {
                request.setStatus(RequestStatus.OVERDUE);
                requestStorage.save(request);
                count++;
                log.warn("Request {} marked as overdue", request.getId());
            }
        }

        log.info("Overdue check completed. {} requests marked as overdue.", count);
    }

    /**
     * Gibt die Statistiken für das Dashboard zurück.
     */
    public RequestStats getStats() {
        List<CustomsRequest> outgoing = findOutgoingRequests();
        List<CustomsRequest> incoming = findIncomingRequests();

        return new RequestStats(
                outgoing.size(),
                outgoing.stream().filter(CustomsRequest::isOpen).count(),
                outgoing.stream().filter(r -> r.getStatus() == RequestStatus.ANSWERED).count(),
                outgoing.stream().filter(r -> r.getStatus() == RequestStatus.OVERDUE).count(),
                incoming.size(),
                incoming.stream().filter(CustomsRequest::isOpen).count(),
                incoming.stream().filter(CustomsRequest::requiresAction).count()
        );
    }

    /**
     * Record für Request-Statistiken.
     */
    public record RequestStats(
            long outgoingTotal,
            long outgoingOpen,
            long outgoingAnswered,
            long outgoingOverdue,
            long incomingTotal,
            long incomingOpen,
            long incomingRequiresAction
    ) {}
}
