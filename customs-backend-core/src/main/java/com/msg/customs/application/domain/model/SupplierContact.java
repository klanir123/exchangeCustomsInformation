package com.msg.customs.application.domain.model;

import lombok.Data;

/**
 * Supplier Contact (Lieferanten-Kontakt) domain object.
 * Gemäß Lastenheft FR-1.6: Lieferanten-Kontakte verwalten.
 */
@Data
public class SupplierContact {

	private Long id;

	/**
	 * Name des Ansprechpartners (contactPerson gemäß Aspektmodell)
	 */
	private String contactPerson;

	/**
	 * Telefonnummer (phoneNumber gemäß Aspektmodell)
	 */
	private String phoneNumber;

	/**
	 * E-Mail-Adresse (email gemäß Aspektmodell)
	 */
	private String email;

	/**
	 * Position/Funktion (jobTitle gemäß Aspektmodell)
	 */
	private String jobTitle;

	/**
	 * Kennzeichnung des Hauptansprechpartners für Zollfragen
	 */
	private boolean primary;

	/**
	 * Bevorzugt für CustomsNotification
	 */
	private boolean preferredForNotifications;
}
