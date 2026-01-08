package com.msg.customs.application.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.msg.customs.model.SupplierStatus;

import lombok.Data;

/**
 * Supplier (Lieferant) domain object.
 * Gemäß Lastenheft Epic 1: Lieferantenverwaltung.
 */
@Data
public class Supplier {

	/**
	 * BPNL - Business Partner Number Legal (fachlicher Schlüssel).
	 * Format: "BPNL" + 12 alphanumerische Zeichen
	 */
	private String bpnl;

	/**
	 * Firmenname des Lieferanten
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
	 * Land (ISO 3166-1 alpha-2)
	 */
	private String country;

	/**
	 * Status des Lieferanten (Aktiv, Inaktiv, Gesperrt)
	 */
	private SupplierStatus status;

	/**
	 * Liste der Ansprechpartner
	 */
	private List<SupplierContact> contacts = new ArrayList<>();

	/**
	 * Anlagedatum
	 */
	private LocalDateTime createdAt;

	/**
	 * Letzte Änderung
	 */
	private LocalDateTime updatedAt;

	/**
	 * Prüft ob der Lieferant aktiv ist.
	 */
	public boolean isActive() {
		return status == SupplierStatus.Active;
	}

	/**
	 * Gibt den primären Kontakt zurück (falls vorhanden).
	 */
	public SupplierContact getPrimaryContact() {
		return contacts.stream()
			.filter(SupplierContact::isPrimary)
			.findFirst()
			.orElse(contacts.isEmpty() ? null : contacts.get(0));
	}
}
