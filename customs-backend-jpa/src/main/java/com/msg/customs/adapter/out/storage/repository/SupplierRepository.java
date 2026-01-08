package com.msg.customs.adapter.out.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msg.customs.adapter.out.storage.model.SupplierEntity;
import com.msg.customs.model.SupplierStatus;

/**
 * Repository für Lieferanten.
 * BPNL ist der Primary Key (String).
 */
@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, String> {

	/**
	 * Findet alle Lieferanten mit einem bestimmten Status.
	 */
	List<SupplierEntity> findByStatus(SupplierStatus status);

	/**
	 * Findet alle aktiven Lieferanten.
	 */
	default List<SupplierEntity> findAllActive() {
		return findByStatus(SupplierStatus.Active);
	}

	/**
	 * Sucht Lieferanten nach Name (Teilsuche, case-insensitive).
	 */
	List<SupplierEntity> findByNameContainingIgnoreCase(String name);

	/**
	 * Findet alle Lieferanten aus einem bestimmten Land.
	 */
	List<SupplierEntity> findByCountry(String country);

	/**
	 * Prüft ob eine BPNL bereits existiert.
	 */
	boolean existsByBpnl(String bpnl);

	/**
	 * Sucht Lieferanten nach BPNL (exakte Suche) oder Name (Teilsuche).
	 */
	@Query("SELECT s FROM SupplierEntity s WHERE s.bpnl = :search OR LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%'))")
	List<SupplierEntity> searchByBpnlOrName(@Param("search") String search);

	/**
	 * Findet alle Lieferanten mit einem bestimmten Status und Land.
	 */
	List<SupplierEntity> findByStatusAndCountry(SupplierStatus status, String country);
}
