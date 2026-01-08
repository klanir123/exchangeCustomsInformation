package com.msg.customs.adapter.in.web.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DTO zum Erstellen/Bearbeiten einer CustomsRequest.
 * Enthält nur die Felder die beim Anlegen/Bearbeiten erforderlich sind.
 */
@Data
public class CreateRequestDto {

    /**
     * ID der Anfrage (nur beim Bearbeiten vorhanden).
     */
    @JsonProperty("id")
    private Long id;

    /**
     * BPNL des Lieferanten, an den die Anfrage gerichtet ist.
     */
    @JsonProperty("supplierBpnl")
    private String supplierBpnl;

    /**
     * Angefragte Sachnummer (Customer Part ID / SNR).
     */
    @JsonProperty("customerPartId")
    private String customerPartId;

    /**
     * Gewünschtes Handelsabkommen.
     * z.B. "EU-MX", "EU-CA"
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
     * Antwortfrist in Tagen (Standard: 14).
     */
    @JsonProperty("responseDays")
    private Integer responseDays = 14;

    /**
     * Freitext-Bemerkung zur Anfrage.
     */
    @JsonProperty("remarks")
    private String remarks;
}
