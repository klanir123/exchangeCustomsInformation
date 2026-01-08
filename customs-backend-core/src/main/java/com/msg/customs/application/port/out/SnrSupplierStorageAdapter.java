package com.msg.customs.application.port.out;

import java.util.List;
import java.util.Optional;

import com.msg.customs.application.domain.model.SnrSupplierLink;

/**
 * Port für den Speicherzugriff auf SNR-Supplier-Verknüpfungen.
 */
public interface SnrSupplierStorageAdapter {

	/**
	 * Speichert eine Verknüpfung.
	 */
	SnrSupplierLink save(SnrSupplierLink link);

	/**
	 * Löscht eine Verknüpfung.
	 */
	void delete(Long masterpartId, String supplierBpnl);

	/**
	 * Findet alle Verknüpfungen für eine SNR.
	 */
	List<SnrSupplierLink> findByMasterpartId(Long masterpartId);

	/**
	 * Findet alle Verknüpfungen für einen Lieferanten.
	 */
	List<SnrSupplierLink> findBySupplierBpnl(String bpnl);

	/**
	 * Findet eine spezifische Verknüpfung.
	 */
	Optional<SnrSupplierLink> findById(Long masterpartId, String supplierBpnl);

	/**
	 * Prüft ob eine Verknüpfung existiert.
	 */
	boolean exists(Long masterpartId, String supplierBpnl);

	/**
	 * Setzt den Primary-Status für alle Verknüpfungen einer SNR zurück.
	 */
	void resetPrimaryForSnr(Long masterpartId);
}
