package com.msg.customs.adapter.out.storage.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.msg.customs.model.NotificationDirection;
import com.msg.customs.model.RequestStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Database representation of a Customs Request (RequestCustomsInformation).
 * Ermöglicht das Anfragen von Zollinformationen/LLE-Daten bei Lieferanten.
 *
 * User Stories:
 * - 5.1: Request Entity für Zoll-Anfragen
 * - 5.3: Basis für API-Endpunkte
 */
@Getter
@Setter
@ToString(exclude = {"header", "supplier", "responseNotification"})
@Entity
@Table(name = "customs_request")
public class CustomsRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Notification-Header mit Metadaten.
     * Enthält messageId, sender/receiver BPNL, etc.
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "header_id")
    private NotificationHeaderEntity header;

    /**
     * Angefragte Sachnummer (Customer Part ID / SNR).
     */
    @Column(name = "customer_part_id", length = 100)
    private String customerPartId;

    /**
     * Lieferant, an den die Anfrage gerichtet ist.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_bpnl")
    private SupplierEntity supplier;

    /**
     * Gewünschtes Handelsabkommen.
     * z.B. "EU-MX", "EU-CA"
     */
    @Column(name = "trade_relation", length = 50)
    private String tradeRelation;

    /**
     * Gewünschtes Gültigkeitsdatum (von).
     */
    @Column(name = "requested_valid_from")
    private LocalDate requestedValidFrom;

    /**
     * Gewünschtes Gültigkeitsdatum (bis).
     */
    @Column(name = "requested_valid_until")
    private LocalDate requestedValidUntil;

    /**
     * Status der Anfrage.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RequestStatus status = RequestStatus.DRAFT;

    /**
     * Richtung der Anfrage (INCOMING/OUTGOING).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false)
    private NotificationDirection direction;

    /**
     * Zeitpunkt des Versands.
     */
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    /**
     * Zeitpunkt der Beantwortung.
     */
    @Column(name = "answered_at")
    private LocalDateTime answeredAt;

    /**
     * Grund bei Ablehnung.
     */
    @Column(name = "rejection_reason", length = 1000)
    private String rejectionReason;

    /**
     * Freitext-Bemerkung zur Anfrage.
     */
    @Column(name = "remarks", length = 2000)
    private String remarks;

    /**
     * Verknüpfung zur Antwort-Notification (falls beantwortet).
     * Referenziert die CustomsNotification mit den LLE-Daten.
     */
    @Column(name = "response_notification_id")
    private Long responseNotificationId;

    /**
     * Zeitpunkt der Erstellung.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Zeitpunkt der letzten Aktualisierung.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = RequestStatus.DRAFT;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Markiert die Anfrage als versendet.
     */
    public void markAsSent() {
        this.status = RequestStatus.SENT;
        this.sentAt = LocalDateTime.now();
        if (this.header != null) {
            this.header.setSentDateTime(this.sentAt);
        }
    }

    /**
     * Markiert die Anfrage als beantwortet.
     */
    public void markAsAnswered(Long responseNotificationId) {
        this.status = RequestStatus.ANSWERED;
        this.answeredAt = LocalDateTime.now();
        this.responseNotificationId = responseNotificationId;
    }

    /**
     * Markiert die Anfrage als abgelehnt.
     */
    public void markAsRejected(String reason) {
        this.status = RequestStatus.REJECTED;
        this.answeredAt = LocalDateTime.now();
        this.rejectionReason = reason;
    }

    /**
     * Prüft ob die Anfrage überfällig ist.
     */
    public boolean isOverdue() {
        if (status != RequestStatus.SENT && status != RequestStatus.RECEIVED && status != RequestStatus.IN_PROGRESS) {
            return false;
        }
        if (header == null || header.getExpectedResponseBy() == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(header.getExpectedResponseBy());
    }
}
