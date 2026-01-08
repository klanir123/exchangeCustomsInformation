package com.msg.customs.application.port.out;

import java.util.List;
import java.util.Optional;

import com.msg.customs.application.domain.model.Supplier;
import com.msg.customs.application.domain.model.SupplierContact;
import com.msg.customs.model.SupplierStatus;

/**
 * Port für die Persistierung von Lieferanten.
 */
public interface SupplierStorageAdapter {

	/**
	 * Lädt alle Lieferanten.
	 */
	List<Supplier> findAll();

	/**
	 * Lädt alle Lieferanten mit einem bestimmten Status.
	 */
	List<Supplier> findByStatus(SupplierStatus status);

	/**
	 * Lädt einen Lieferanten nach BPNL.
	 */
	Optional<Supplier> findByBpnl(String bpnl);

	/**
	 * Sucht Lieferanten nach BPNL oder Name.
	 */
	List<Supplier> searchByBpnlOrName(String query);

	/**
	 * Prüft ob eine BPNL existiert.
	 */
	boolean existsByBpnl(String bpnl);

	/**
	 * Speichert einen Lieferanten (create oder update).
	 */
	Supplier save(Supplier supplier);

	/**
	 * Löscht einen Lieferanten.
	 */
	void deleteByBpnl(String bpnl);

	/**
	 * Speichert einen Kontakt.
	 */
	SupplierContact saveContact(String bpnl, SupplierContact contact);

	/**
	 * Löscht einen Kontakt.
	 */
	void deleteContact(String bpnl, Long contactId);

	/**
	 * Lädt einen Kontakt nach ID.
	 */
	Optional<SupplierContact> findContactById(Long contactId);
}
