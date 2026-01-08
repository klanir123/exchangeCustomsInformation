package com.msg.customs.application.port.in;

import java.util.List;

import com.msg.customs.application.domain.model.Supplier;
import com.msg.customs.model.SupplierStatus;

/**
 * Use Case zum Laden von Lieferanten.
 */
public interface LoadSupplierListUseCase {

	/**
	 * Lädt alle Lieferanten.
	 */
	List<Supplier> loadAllSuppliers();

	/**
	 * Lädt alle Lieferanten mit einem bestimmten Status.
	 */
	List<Supplier> loadSuppliersByStatus(SupplierStatus status);

	/**
	 * Lädt nur aktive Lieferanten (für Dropdown-Auswahl).
	 */
	List<Supplier> loadActiveSuppliers();

	/**
	 * Lädt einen Lieferanten nach BPNL.
	 */
	Supplier loadSupplierByBpnl(String bpnl);

	/**
	 * Sucht Lieferanten nach BPNL oder Name.
	 */
	List<Supplier> searchSuppliers(String query);

	/**
	 * Prüft ob eine BPNL bereits existiert.
	 */
	boolean existsByBpnl(String bpnl);
}
