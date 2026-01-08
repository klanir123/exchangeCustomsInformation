package com.msg.customs.application.port.in;

import java.util.List;

import com.msg.customs.application.domain.model.SnrSupplierLink;

/**
 * Use Case für die Verwaltung der SNR-Lieferanten-Verknüpfungen.
 * Implementiert FR-2.3 aus dem Lastenheft.
 */
public interface ManageSnrSupplierUseCase {

	/**
	 * Fügt einen Lieferanten zu einer SNR hinzu.
	 */
	void addSupplierToSnr(Long masterpartId, String supplierBpnl, boolean isPrimary, String note);

	/**
	 * Entfernt einen Lieferanten von einer SNR.
	 */
	void removeSupplierFromSnr(Long masterpartId, String supplierBpnl);

	/**
	 * Setzt einen Lieferanten als primären Lieferanten für eine SNR.
	 */
	void setPrimarySupplier(Long masterpartId, String supplierBpnl);

	/**
	 * Aktualisiert die Notiz einer Verknüpfung.
	 */
	void updateLinkNote(Long masterpartId, String supplierBpnl, String note);

	/**
	 * Lädt alle Lieferanten für eine SNR.
	 */
	List<SnrSupplierLink> getSuppliersForSnr(Long masterpartId);

	/**
	 * Lädt alle SNRs für einen Lieferanten.
	 */
	List<SnrSupplierLink> getSnrsForSupplier(String supplierBpnl);

	/**
	 * Prüft ob eine Verknüpfung existiert.
	 */
	boolean linkExists(Long masterpartId, String supplierBpnl);
}
