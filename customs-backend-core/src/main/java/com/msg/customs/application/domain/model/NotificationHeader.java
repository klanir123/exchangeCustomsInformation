package com.msg.customs.application.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

/**
 * NotificationHeader domain object.
 * Enthält Metadaten für CustomsNotification und CustomsRequest.
 *
 * Basiert auf dem Catena-X Industry Core Digital Twin Event API Standard.
 */
@Data
public class NotificationHeader {

    /**
     * Eindeutige Message-ID (UUID).
     */
    private String messageId;

    /**
     * Context der Nachricht.
     * z.B. "IndustryCore-DigitalTwinEventAPI-ConnectToParent:3.0.0"
     */
    private String context;

    /**
     * Zeitpunkt des Versands.
     */
    private LocalDateTime sentDateTime;

    /**
     * BPNL des Senders.
     */
    private String senderBpnl;

    /**
     * BPNL des Empfängers.
     */
    private String receiverBpnl;

    /**
     * Erwartete Antwortzeit.
     */
    private LocalDateTime expectedResponseBy;

    /**
     * Version des Notification-Formats.
     */
    private String version;

    /**
     * Zeitpunkt der Erstellung.
     */
    private LocalDateTime createdAt;

    /**
     * Zeitpunkt der letzten Aktualisierung.
     */
    private LocalDateTime updatedAt;

    /**
     * Erstellt einen neuen Header mit generierter Message-ID.
     */
    public static NotificationHeader createNew(String senderBpnl, String receiverBpnl) {
        NotificationHeader header = new NotificationHeader();
        header.setMessageId(UUID.randomUUID().toString());
        header.setSenderBpnl(senderBpnl);
        header.setReceiverBpnl(receiverBpnl);
        header.setContext("IndustryCore-CustomsKIT-RequestCustomsInformation:1.0.0");
        header.setVersion("1.0.0");
        header.setCreatedAt(LocalDateTime.now());
        return header;
    }

    /**
     * Erstellt einen neuen Header mit Antwortfrist.
     */
    public static NotificationHeader createNewWithDeadline(String senderBpnl, String receiverBpnl, int responseDays) {
        NotificationHeader header = createNew(senderBpnl, receiverBpnl);
        header.setExpectedResponseBy(LocalDateTime.now().plusDays(responseDays));
        return header;
    }
}
