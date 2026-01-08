package com.msg.customs.adapter.out.storage.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msg.customs.adapter.out.storage.model.CustomsRequestEntity;
import com.msg.customs.model.NotificationDirection;
import com.msg.customs.model.RequestStatus;

/**
 * Repository für CustomsRequest-Entities.
 * Ermöglicht Zugriff auf Zoll-Anfragen.
 */
@Repository
public interface CustomsRequestRepository extends JpaRepository<CustomsRequestEntity, Long> {

    /**
     * Findet alle Anfragen nach Richtung (INCOMING/OUTGOING).
     */
    List<CustomsRequestEntity> findByDirectionOrderByCreatedAtDesc(NotificationDirection direction);

    /**
     * Findet alle Anfragen nach Status.
     */
    List<CustomsRequestEntity> findByStatusOrderByCreatedAtDesc(RequestStatus status);

    /**
     * Findet alle Anfragen nach Status und Richtung.
     */
    List<CustomsRequestEntity> findByStatusAndDirectionOrderByCreatedAtDesc(RequestStatus status, NotificationDirection direction);

    /**
     * Findet Anfragen für einen bestimmten Lieferanten.
     */
    List<CustomsRequestEntity> findBySupplierBpnlOrderByCreatedAtDesc(String supplierBpnl);

    /**
     * Findet Anfragen für eine bestimmte Sachnummer.
     */
    List<CustomsRequestEntity> findByCustomerPartIdOrderByCreatedAtDesc(String customerPartId);

    /**
     * Findet alle überfälligen Anfragen.
     * Anfragen sind überfällig wenn:
     * - Status ist SENT, RECEIVED oder IN_PROGRESS
     * - expectedResponseBy liegt in der Vergangenheit
     */
    @Query("SELECT r FROM CustomsRequestEntity r " +
           "WHERE r.status IN :statuses " +
           "AND r.header.expectedResponseBy < :now")
    List<CustomsRequestEntity> findOverdueRequests(
            @Param("statuses") List<RequestStatus> statuses,
            @Param("now") LocalDateTime now);

    /**
     * Findet alle überfälligen Anfragen (Convenience-Methode).
     */
    default List<CustomsRequestEntity> findOverdueRequests() {
        return findOverdueRequests(
            List.of(RequestStatus.SENT, RequestStatus.RECEIVED, RequestStatus.IN_PROGRESS),
            LocalDateTime.now()
        );
    }

    /**
     * Findet Anfrage anhand der Message-ID.
     */
    @Query("SELECT r FROM CustomsRequestEntity r WHERE r.header.messageId = :messageId")
    Optional<CustomsRequestEntity> findByMessageId(@Param("messageId") String messageId);

    /**
     * Zählt offene Anfragen nach Richtung.
     */
    @Query("SELECT COUNT(r) FROM CustomsRequestEntity r " +
           "WHERE r.direction = :direction " +
           "AND r.status IN :statuses")
    long countOpenRequests(
            @Param("direction") NotificationDirection direction,
            @Param("statuses") List<RequestStatus> statuses);

    /**
     * Zählt offene ausgehende Anfragen.
     */
    default long countOpenOutgoingRequests() {
        return countOpenRequests(
            NotificationDirection.OUTGOING,
            List.of(RequestStatus.SENT, RequestStatus.RECEIVED, RequestStatus.IN_PROGRESS)
        );
    }

    /**
     * Zählt offene eingehende Anfragen.
     */
    default long countOpenIncomingRequests() {
        return countOpenRequests(
            NotificationDirection.INCOMING,
            List.of(RequestStatus.RECEIVED, RequestStatus.IN_PROGRESS)
        );
    }

    /**
     * Zählt überfällige Anfragen.
     */
    @Query("SELECT COUNT(r) FROM CustomsRequestEntity r " +
           "WHERE r.status IN ('SENT', 'RECEIVED', 'IN_PROGRESS') " +
           "AND r.header.expectedResponseBy < CURRENT_TIMESTAMP")
    long countOverdueRequests();
}
