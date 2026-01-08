package com.msg.customs.adapter.out.storage.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msg.customs.adapter.out.storage.model.CustomsEntity;
import com.msg.customs.model.LleStatus;

@Repository
public interface CustomsRepository extends JpaRepository<CustomsEntity, Long> {

	CustomsEntity findByCatenaxId(String catenaxId);

	List<CustomsEntity> findByCatenaxIdIn(List<String> catenaxIds);

	// ==================== Phase 3: LLE-Erweiterungen ====================

	/**
	 * Findet alle LLEs mit einem bestimmten Status.
	 */
	List<CustomsEntity> findByLleStatus(LleStatus status);

	/**
	 * Findet alle LLEs, die in den nächsten X Tagen ablaufen.
	 */
	@Query("SELECT c FROM CustomsEntity c WHERE c.validUntil BETWEEN :today AND :futureDate")
	List<CustomsEntity> findExpiringBetween(@Param("today") LocalDate today, @Param("futureDate") LocalDate futureDate);

	/**
	 * Findet alle abgelaufenen LLEs.
	 */
	@Query("SELECT c FROM CustomsEntity c WHERE c.validUntil < :today")
	List<CustomsEntity> findExpired(@Param("today") LocalDate today);

	/**
	 * Zählt LLEs pro Status.
	 */
	long countByLleStatus(LleStatus status);

	/**
	 * Findet alle LLEs eines Lieferanten.
	 */
	List<CustomsEntity> findBySupplierBpnl(String supplierBpnl);

}
