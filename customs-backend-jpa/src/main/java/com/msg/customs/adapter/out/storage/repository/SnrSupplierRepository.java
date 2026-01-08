package com.msg.customs.adapter.out.storage.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msg.customs.adapter.out.storage.model.SnrSupplierEntity;
import com.msg.customs.adapter.out.storage.model.SnrSupplierId;

/**
 * Repository für die SNR-Supplier Verknüpfungen.
 */
@Repository
public interface SnrSupplierRepository extends JpaRepository<SnrSupplierEntity, SnrSupplierId> {

	/**
	 * Findet alle Supplier-Verknüpfungen für eine SNR.
	 */
	List<SnrSupplierEntity> findByMasterpartId(Long masterpartId);

	/**
	 * Findet alle SNR-Verknüpfungen für einen Supplier.
	 */
	List<SnrSupplierEntity> findBySupplierBpnl(String bpnl);

	/**
	 * Findet den primären Lieferanten für eine SNR.
	 */
	Optional<SnrSupplierEntity> findByMasterpartIdAndPrimaryTrue(Long masterpartId);

	/**
	 * Prüft ob eine Verknüpfung existiert.
	 */
	boolean existsByMasterpartIdAndSupplierBpnl(Long masterpartId, String bpnl);

	/**
	 * Löscht alle Verknüpfungen für eine SNR.
	 */
	void deleteByMasterpartId(Long masterpartId);

	/**
	 * Löscht alle Verknüpfungen für einen Supplier.
	 */
	void deleteBySupplierBpnl(String bpnl);

	/**
	 * Findet alle SNRs eines aktiven Lieferanten.
	 */
	@Query("SELECT s FROM SnrSupplierEntity s WHERE s.supplier.status = 'Active' AND s.masterpart.id = :masterpartId")
	List<SnrSupplierEntity> findActiveSuppliersByMasterpartId(@Param("masterpartId") Long masterpartId);

	/**
	 * Zählt die Anzahl der Lieferanten für eine SNR.
	 */
	long countByMasterpartId(Long masterpartId);
}
