package com.msg.customs.application.port.in;

import com.msg.customs.application.domain.model.Supplier;
import com.msg.customs.application.domain.model.SupplierContact;
import com.msg.customs.model.SupplierStatus;

/**
 * Use Case zum Speichern und Verwalten von Lieferanten.
 */
public interface SaveSupplierUseCase {

	/**
	 * Erstellt einen neuen Lieferanten.
	 * @throws IllegalArgumentException wenn BPNL ungültig oder bereits vorhanden
	 */
	void createSupplier(Supplier supplier);

	/**
	 * Aktualisiert einen bestehenden Lieferanten.
	 * BPNL kann nicht geändert werden.
	 */
	void updateSupplier(String bpnl, Supplier supplier);

	/**
	 * Ändert den Status eines Lieferanten.
	 */
	void updateSupplierStatus(String bpnl, SupplierStatus status);

	/**
	 * Löscht einen Lieferanten.
	 * @throws IllegalStateException wenn noch verknüpfte SNRs/LLEs existieren
	 */
	void deleteSupplier(String bpnl);

	/**
	 * Fügt einen Kontakt zu einem Lieferanten hinzu.
	 */
	void addContact(String bpnl, SupplierContact contact);

	/**
	 * Aktualisiert einen Kontakt.
	 */
	void updateContact(String bpnl, Long contactId, SupplierContact contact);

	/**
	 * Löscht einen Kontakt.
	 */
	void deleteContact(String bpnl, Long contactId);

	/**
	 * Setzt einen Kontakt als primären Ansprechpartner.
	 */
	void setContactAsPrimary(String bpnl, Long contactId);
}
