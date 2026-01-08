package com.msg.customs.adapter.in.web.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DTO zum Simulieren einer eingehenden Anfrage (vom EDC empfangen).
 * Wird verwendet um eingehende Anfragen zu testen.
 */
@Data
public class ReceiveRequestDto {

    /**
     * BPNL des Absenders (Kunde, der die Anfrage stellt).
     */
    @JsonProperty("senderBpnl")
    private String senderBpnl;

    /**
     * Angefragte Sachnummer (Customer Part ID / SNR).
     */
    @JsonProperty("customerPartId")
    private String customerPartId;

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
     * Erwartete Antwort bis (Deadline).
     */
    @JsonProperty("expectedResponseBy")
    private LocalDateTime expectedResponseBy;

    /**
     * Freitext-Bemerkung zur Anfrage.
     */
    @JsonProperty("remarks")
    private String remarks;
}
