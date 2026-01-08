package com.msg.customs.adapter.in.web.model;

import lombok.Data;

/**
 * DTO für Lieferanten-Kontakt.
 * Gemäß Lastenheft FR-1.6 und Aspektmodell contactInformation.
 */
@Data
public class SupplierContactDto {

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
	 * Hauptansprechpartner für Zollfragen
	 */
	private boolean primary;

	/**
	 * Bevorzugt für CustomsNotification
	 */
	private boolean preferredForNotifications;
}
