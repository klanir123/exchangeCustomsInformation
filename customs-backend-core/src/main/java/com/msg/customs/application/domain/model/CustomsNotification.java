package com.msg.customs.application.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.msg.customs.model.NotificationDirection;
import com.msg.customs.model.NotificationStatus;

import lombok.Data;

/**
 * Domain model für CustomsNotification.
 * Repräsentiert eine Benachrichtigung mit LLE-Daten.
 */
@Data
public class CustomsNotification {

    private Long id;
    private NotificationHeader header;
    private List<NotificationItem> listOfItems = new ArrayList<>();
    private NotificationDirection direction;
    private NotificationStatus status;
    private String supplierBpnl;
    private String supplierName;
    private Long originalRequestId;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Erstellt eine neue ausgehende Notification.
     */
    public static CustomsNotification createOutgoing(String senderBpnl, String receiverBpnl) {
        CustomsNotification notification = new CustomsNotification();
        notification.setHeader(NotificationHeader.createNew(senderBpnl, receiverBpnl));
        notification.setDirection(NotificationDirection.OUTGOING);
        notification.setStatus(NotificationStatus.DRAFT);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());
        return notification;
    }

    /**
     * Fügt ein Item hinzu.
     */
    public void addItem(NotificationItem item) {
        if (listOfItems == null) {
            listOfItems = new ArrayList<>();
        }
        listOfItems.add(item);
    }

    /**
     * Prüft ob die Notification bearbeitet werden kann.
     */
    public boolean isEditable() {
        return status != null && status.isEditable();
    }

    /**
     * Prüft ob die Notification gesendet werden kann.
     */
    public boolean canBeSent() {
        return status != null && status.canBeSent() && hasItems();
    }

    /**
     * Prüft ob Items vorhanden sind.
     */
    public boolean hasItems() {
        return listOfItems != null && !listOfItems.isEmpty();
    }

    /**
     * Gibt die Anzahl der Items zurück.
     */
    public int getItemCount() {
        return listOfItems != null ? listOfItems.size() : 0;
    }

    /**
     * Gibt die Anzahl der nicht übernommenen Items zurück.
     */
    public long getUnadoptedItemCount() {
        if (listOfItems == null) return 0;
        return listOfItems.stream().filter(item -> !item.isAdopted()).count();
    }

    /**
     * Prüft ob es eingehend ist.
     */
    public boolean isIncoming() {
        return direction == NotificationDirection.INCOMING;
    }

    /**
     * Prüft ob es ausgehend ist.
     */
    public boolean isOutgoing() {
        return direction == NotificationDirection.OUTGOING;
    }
}
