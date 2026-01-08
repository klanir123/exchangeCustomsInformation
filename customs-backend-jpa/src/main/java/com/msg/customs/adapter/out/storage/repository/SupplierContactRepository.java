package com.msg.customs.adapter.out.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msg.customs.adapter.out.storage.model.SupplierContactEntity;

/**
 * Repository für Lieferanten-Kontakte.
 */
@Repository
public interface SupplierContactRepository extends JpaRepository<SupplierContactEntity, Long> {

	/**
	 * Findet alle Kontakte eines Lieferanten.
	 */
	List<SupplierContactEntity> findBySupplierBpnl(String supplierBpnl);

	/**
	 * Findet den primären Kontakt eines Lieferanten.
	 */
	SupplierContactEntity findBySupplierBpnlAndPrimaryTrue(String supplierBpnl);

	/**
	 * Löscht alle Kontakte eines Lieferanten.
	 */
	void deleteBySupplierBpnl(String supplierBpnl);

	/**
	 * Löscht einen Kontakt nach ID (mit Modifying für direkte DB-Operation).
	 */
	@org.springframework.data.jpa.repository.Modifying
	@org.springframework.data.jpa.repository.Query("DELETE FROM SupplierContactEntity c WHERE c.id = :contactId")
	int deleteContactById(@org.springframework.data.repository.query.Param("contactId") Long contactId);
}
