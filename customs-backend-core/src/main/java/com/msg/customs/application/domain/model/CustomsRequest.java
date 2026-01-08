package com.msg.customs.application.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.msg.customs.model.NotificationDirection;
import com.msg.customs.model.RequestStatus;

import lombok.Data;

/**
 * CustomsRequest domain object.
 * Repräsentiert eine Anfrage für Zollinformationen/LLE-Daten.
 *
 * User Stories:
 * - 5.1: Request Entity für Zoll-Anfragen
 * - 5.3: Basis für API-Endpunkte
 */
@Data
public class CustomsRequest {

    /**
     * Eindeutige ID der Anfrage.
     */
    private Long id;

    /**
     * Notification-Header mit Metadaten.
     */
    private NotificationHeader header;

    /**
     * Angefragte Sachnummer (Customer Part ID / SNR).
     */
    private String customerPartId;

    /**
     * BPNL des Lieferanten.
     */
    private String supplierBpnl;

    /**
     * Name des Lieferanten (für Anzeige).
     */
    private String supplierName;

    /**
     * Gewünschtes Handelsabkommen.
     */
    private String tradeRelation;

    /**
     * Gewünschtes Gültigkeitsdatum (von).
     */
    private LocalDate requestedValidFrom;

    /**
     * Gewünschtes Gültigkeitsdatum (bis).
     */
    private LocalDate requestedValidUntil;

    /**
     * Status der Anfrage.
     */
    private RequestStatus status;

    /**
     * Richtung der Anfrage (INCOMING/OUTGOING).
     */
    private NotificationDirection direction;

    /**
     * Zeitpunkt des Versands.
     */
    private LocalDateTime sentAt;

    /**
     * Zeitpunkt der Beantwortung.
     */
    private LocalDateTime answeredAt;

    /**
     * Grund bei Ablehnung.
     */
    private String rejectionReason;

    /**
     * Freitext-Bemerkung zur Anfrage.
     */
    private String remarks;

    /**
     * ID der Antwort-Notification.
     */
    private Long responseNotificationId;

    /**
     * Zeitpunkt der Erstellung.
     */
    private LocalDateTime createdAt;

    /**
     * Zeitpunkt der letzten Aktualisierung.
     */
    private LocalDateTime updatedAt;

    /**
     * Erstellt eine neue ausgehende Anfrage.
     */
    public static CustomsRequest createOutgoing(String senderBpnl, String supplierBpnl, String customerPartId) {
        CustomsRequest request = new CustomsRequest();
        request.setHeader(NotificationHeader.createNewWithDeadline(senderBpnl, supplierBpnl, 14));
        request.setSupplierBpnl(supplierBpnl);
        request.setCustomerPartId(customerPartId);
        request.setStatus(RequestStatus.DRAFT);
        request.setDirection(NotificationDirection.OUTGOING);
        request.setCreatedAt(LocalDateTime.now());
        return request;
    }

    /**
     * Prüft ob die Anfrage noch offen ist.
     */
    public boolean isOpen() {
        return status != null && status.isOpen();
    }

    /**
     * Prüft ob die Anfrage abgeschlossen ist.
     */
    public boolean isClosed() {
        return status != null && status.isClosed();
    }

    /**
     * Prüft ob die Anfrage überfällig ist.
     */
    public boolean isOverdue() {
        if (!isOpen()) {
            return false;
        }
        if (header == null || header.getExpectedResponseBy() == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(header.getExpectedResponseBy());
    }

    /**
     * Berechnet die verbleibenden Tage bis zur Antwortfrist.
     */
    public long getDaysUntilDeadline() {
        if (header == null || header.getExpectedResponseBy() == null) {
            return -1;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDateTime.now(), header.getExpectedResponseBy());
    }

    /**
     * Prüft ob auf diese Anfrage reagiert werden muss (eingehend und noch nicht beantwortet).
     */
    public boolean requiresAction() {
        return direction == NotificationDirection.INCOMING && status != null && status.requiresAction();
    }
}
