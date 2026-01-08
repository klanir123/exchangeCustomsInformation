package com.msg.customs.adapter.in.web.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msg.customs.model.SupplierStatus;

import lombok.Data;

/**
 * DTO für Lieferanten-Daten.
 * Gemäß Lastenheft Epic 1: Lieferantenverwaltung.
 */
@Data
public class SupplierDto {

	/**
	 * BPNL - Business Partner Number Legal (fachlicher Schlüssel).
	 * Format: "BPNL" + 12 alphanumerische Zeichen (z.B. BPNL000000000AAA)
	 */
	@JsonProperty("bpnl")
	private String bpnl;

	/**
	 * Firmenname
	 */
	private String name;

	/**
	 * Straße und Hausnummer
	 */
	private String street;

	/**
	 * Postleitzahl
	 */
	private String postalCode;

	/**
	 * Ort
	 */
	private String city;

	/**
	 * Land (ISO 3166-1 alpha-2, z.B. "DE")
	 */
	private String country;

	/**
	 * Status (Active, Inactive, Blocked)
	 */
	private SupplierStatus status;

	/**
	 * Kontaktliste
	 */
	private List<SupplierContactDto> contacts;

	/**
	 * Anlagedatum
	 */
	private LocalDateTime createdAt;

	/**
	 * Letzte Änderung
	 */
	private LocalDateTime updatedAt;

	/**
	 * Anzahl verknüpfter SNRs (für Listenansicht)
	 */
	private Integer snrCount;

	/**
	 * Anzahl verknüpfter LLEs (für Listenansicht)
	 */
	private Integer lleCount;
}
