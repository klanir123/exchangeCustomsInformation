package com.msg.customs.adapter.in.web.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DTO für Notification-Header.
 * Enthält Metadaten für CustomsNotification und CustomsRequest.
 */
@Data
public class NotificationHeaderDto {

    /**
     * Eindeutige Message-ID (UUID).
     */
    @JsonProperty("messageId")
    private String messageId;

    /**
     * Context der Nachricht.
     */
    @JsonProperty("context")
    private String context;

    /**
     * Zeitpunkt des Versands.
     */
    @JsonProperty("sentDateTime")
    private LocalDateTime sentDateTime;

    /**
     * BPNL des Senders.
     */
    @JsonProperty("senderBpnl")
    private String senderBpnl;

    /**
     * BPNL des Empfängers.
     */
    @JsonProperty("receiverBpnl")
    private String receiverBpnl;

    /**
     * Erwartete Antwortzeit.
     */
    @JsonProperty("expectedResponseBy")
    private LocalDateTime expectedResponseBy;

    /**
     * Version des Notification-Formats.
     */
    @JsonProperty("version")
    private String version;
}
