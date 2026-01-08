package com.msg.customs.adapter.in.web.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msg.customs.model.NotificationDirection;
import com.msg.customs.model.RequestStatus;

import lombok.Data;

/**
 * DTO für CustomsRequest (RequestCustomsInformation).
 * Repräsentiert eine Anfrage für Zollinformationen/LLE-Daten.
 *
 * User Stories:
 * - 5.1: Request Entity für Zoll-Anfragen
 * - 5.3: Basis für API-Endpunkte
 */
@Data
public class CustomsRequestDto {

    /**
     * Eindeutige ID der Anfrage.
     */
    @JsonProperty("id")
    private Long id;

    /**
     * Notification-Header mit Metadaten.
     */
    @JsonProperty("header")
    private NotificationHeaderDto header;

    /**
     * Angefragte Sachnummer (Customer Part ID / SNR).
     */
    @JsonProperty("customerPartId")
    private String customerPartId;

    /**
     * BPNL des Lieferanten.
     */
    @JsonProperty("supplierBpnl")
    private String supplierBpnl;

    /**
     * Name des Lieferanten (für Anzeige).
     */
    @JsonProperty("supplierName")
    private String supplierName;

    /**
     * Gewünschtes Handelsabkommen.
     */
    @JsonProperty("tradeRelation")
    private String tradeRelation;

    /**
     * Gewünschtes Gültigkeitsdatum (von).
     */
    @JsonProperty("requestedValidFrom")
    private LocalDate requestedValidFrom;

    /**
     * Gewünschtes Gültigkeitsdatum (bis).
     */
    @JsonProperty("requestedValidUntil")
    private LocalDate requestedValidUntil;

    /**
     * Status der Anfrage.
     */
    @JsonProperty("status")
    private RequestStatus status;

    /**
     * Anzeigename des Status (für UI).
     */
    @JsonProperty("statusDisplayName")
    private String statusDisplayName;

    /**
     * Richtung der Anfrage (INCOMING/OUTGOING).
     */
    @JsonProperty("direction")
    private NotificationDirection direction;

    /**
     * Zeitpunkt des Versands.
     */
    @JsonProperty("sentAt")
    private LocalDateTime sentAt;

    /**
     * Zeitpunkt der Beantwortung.
     */
    @JsonProperty("answeredAt")
    private LocalDateTime answeredAt;

    /**
     * Grund bei Ablehnung.
     */
    @JsonProperty("rejectionReason")
    private String rejectionReason;

    /**
     * Freitext-Bemerkung zur Anfrage.
     */
    @JsonProperty("remarks")
    private String remarks;

    /**
     * ID der Antwort-Notification.
     */
    @JsonProperty("responseNotificationId")
    private Long responseNotificationId;

    /**
     * Zeitpunkt der Erstellung.
     */
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    /**
     * Zeitpunkt der letzten Aktualisierung.
     */
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    /**
     * Ist die Anfrage überfällig?
     */
    @JsonProperty("overdue")
    private boolean overdue;

    /**
     * Verbleibende Tage bis zur Antwortfrist.
     */
    @JsonProperty("daysUntilDeadline")
    private Long daysUntilDeadline;

    /**
     * Erfordert die Anfrage eine Aktion?
     */
    @JsonProperty("requiresAction")
    private boolean requiresAction;
}
