package com.msg.customs.adapter.out.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msg.customs.adapter.out.storage.model.NotificationItemEntity;

/**
 * Repository für NotificationItem.
 */
@Repository
public interface NotificationItemRepository extends JpaRepository<NotificationItemEntity, Long> {

    /**
     * Findet alle Items einer Notification.
     */
    List<NotificationItemEntity> findByNotificationId(Long notificationId);

    /**
     * Findet nicht übernommene Items einer Notification.
     */
    List<NotificationItemEntity> findByNotificationIdAndAdoptedFalse(Long notificationId);

    /**
     * Findet bereits übernommene Items einer Notification.
     */
    List<NotificationItemEntity> findByNotificationIdAndAdoptedTrue(Long notificationId);

    /**
     * Findet Items nach Catena-X ID.
     */
    List<NotificationItemEntity> findByCatenaXId(String catenaXId);

    /**
     * Findet Items nach Kunden-Teilenummer.
     */
    List<NotificationItemEntity> findByCustomerPartId(String customerPartId);

    /**
     * Zählt nicht übernommene Items.
     */
    long countByNotificationIdAndAdoptedFalse(Long notificationId);
}
