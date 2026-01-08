package com.msg.customs.application.port.out;

import java.util.List;
import java.util.Optional;

import com.msg.customs.application.domain.model.CustomsNotification;
import com.msg.customs.application.domain.model.NotificationItem;
import com.msg.customs.model.NotificationDirection;
import com.msg.customs.model.NotificationStatus;

/**
 * Port für die Speicherung von CustomsNotifications.
 */
public interface CustomsNotificationStorageAdapter {

    /**
     * Speichert eine Notification.
     */
    CustomsNotification save(CustomsNotification notification);

    /**
     * Findet eine Notification nach ID.
     */
    Optional<CustomsNotification> findById(Long id);

    /**
     * Findet alle Notifications.
     */
    List<CustomsNotification> findAll();

    /**
     * Findet Notifications nach Richtung.
     */
    List<CustomsNotification> findByDirection(NotificationDirection direction);

    /**
     * Findet Notifications nach Status.
     */
    List<CustomsNotification> findByStatus(NotificationStatus status);

    /**
     * Findet Notifications nach Richtung und Status.
     */
    List<CustomsNotification> findByDirectionAndStatus(
            NotificationDirection direction, NotificationStatus status);

    /**
     * Löscht eine Notification.
     */
    void deleteById(Long id);

    /**
     * Findet ein Item nach ID.
     */
    Optional<NotificationItem> findItemById(Long itemId);

    /**
     * Speichert ein Item.
     */
    NotificationItem saveItem(NotificationItem item);

    /**
     * Zählt unverarbeitete eingehende Notifications.
     */
    long countUnprocessedIncoming();

    /**
     * Zählt Notifications nach Status.
     */
    long countByStatus(NotificationStatus status);

    /**
     * Zählt Notifications nach Richtung.
     */
    long countByDirection(NotificationDirection direction);
}
