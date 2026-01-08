package com.msg.customs.adapter.out.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msg.customs.adapter.out.storage.model.CustomsNotificationEntity;
import com.msg.customs.model.NotificationDirection;
import com.msg.customs.model.NotificationStatus;

/**
 * Repository für CustomsNotification.
 */
@Repository
public interface CustomsNotificationRepository extends JpaRepository<CustomsNotificationEntity, Long> {

    /**
     * Findet alle Notifications nach Richtung.
     */
    List<CustomsNotificationEntity> findByDirection(NotificationDirection direction);

    /**
     * Findet alle Notifications nach Status.
     */
    List<CustomsNotificationEntity> findByStatus(NotificationStatus status);

    /**
     * Findet alle Notifications nach Richtung und Status.
     */
    List<CustomsNotificationEntity> findByDirectionAndStatus(
            NotificationDirection direction, NotificationStatus status);

    /**
     * Findet alle ausgehenden Notifications.
     */
    default List<CustomsNotificationEntity> findOutgoing() {
        return findByDirection(NotificationDirection.OUTGOING);
    }

    /**
     * Findet alle eingehenden Notifications.
     */
    default List<CustomsNotificationEntity> findIncoming() {
        return findByDirection(NotificationDirection.INCOMING);
    }

    /**
     * Findet Notifications nach Sender-BPNL.
     */
    @Query("SELECT n FROM CustomsNotificationEntity n WHERE n.header.senderBpnl = :bpnl")
    List<CustomsNotificationEntity> findBySenderBpnl(@Param("bpnl") String bpnl);

    /**
     * Findet Notifications nach Empfänger-BPNL.
     */
    @Query("SELECT n FROM CustomsNotificationEntity n WHERE n.header.receiverBpnl = :bpnl")
    List<CustomsNotificationEntity> findByReceiverBpnl(@Param("bpnl") String bpnl);

    /**
     * Findet Notifications zu einer Anfrage.
     */
    List<CustomsNotificationEntity> findByOriginalRequestId(Long requestId);

    /**
     * Findet eingehende Notifications die noch nicht verarbeitet wurden.
     */
    @Query("SELECT n FROM CustomsNotificationEntity n WHERE n.direction = 'INCOMING' AND n.status = 'RECEIVED'")
    List<CustomsNotificationEntity> findUnprocessedIncoming();

    /**
     * Zählt Notifications nach Status.
     */
    long countByStatus(NotificationStatus status);

    /**
     * Zählt Notifications nach Richtung.
     */
    long countByDirection(NotificationDirection direction);

    /**
     * Zählt eingehende unverarbeitete Notifications.
     */
    @Query("SELECT COUNT(n) FROM CustomsNotificationEntity n WHERE n.direction = 'INCOMING' AND n.status = 'RECEIVED'")
    long countUnprocessedIncoming();
}
