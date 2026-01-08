package com.msg.customs.adapter.out.storage.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Database representation of a notification header.
 * Enthält Metadaten für CustomsNotification und CustomsRequest.
 *
 * Basiert auf dem Catena-X Industry Core Digital Twin Event API Standard.
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "notification_header")
public class NotificationHeaderEntity {

    /**
     * Eindeutige Message-ID (UUID).
     */
    @Id
    @Column(name = "message_id", length = 36)
    private String messageId;

    /**
     * Context der Nachricht.
     * z.B. "IndustryCore-DigitalTwinEventAPI-ConnectToParent:3.0.0"
     */
    @Column(name = "context", length = 255)
    private String context;

    /**
     * Zeitpunkt des Versands.
     */
    @Column(name = "sent_date_time")
    private LocalDateTime sentDateTime;

    /**
     * BPNL des Senders.
     */
    @Column(name = "sender_bpnl", length = 16, nullable = false)
    private String senderBpnl;

    /**
     * BPNL des Empfängers.
     */
    @Column(name = "receiver_bpnl", length = 16, nullable = false)
    private String receiverBpnl;

    /**
     * Erwartete Antwortzeit.
     * Für RequestCustomsInformation: Frist zur Beantwortung.
     */
    @Column(name = "expected_response_by")
    private LocalDateTime expectedResponseBy;

    /**
     * Version des Notification-Formats.
     */
    @Column(name = "version", length = 20)
    private String version;

    /**
     * Zeitpunkt der Erstellung.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Zeitpunkt der letzten Aktualisierung.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
