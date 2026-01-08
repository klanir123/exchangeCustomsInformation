package com.msg.customs.application.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.domain.model.CustomsNotification;
import com.msg.customs.application.domain.model.NotificationHeader;
import com.msg.customs.application.domain.model.NotificationItem;
import com.msg.customs.application.port.out.CustomsDataStorageAdapter;
import com.msg.customs.application.port.out.CustomsNotificationStorageAdapter;
import com.msg.customs.model.NotificationDirection;
import com.msg.customs.model.NotificationStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service für CustomsNotification.
 * Implementiert die Geschäftslogik für das Senden und Empfangen von LLE-Daten.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomsNotificationService {

    private final CustomsNotificationStorageAdapter notificationStorage;
    private final CustomsDataStorageAdapter customsStorage;

    // Eigene BPNL
    private String ownBpnl = "BPNL000000000OEM";

    public void setOwnBpnl(String ownBpnl) {
        this.ownBpnl = ownBpnl;
    }

    // ==================== Allgemeine Operationen ====================

    public List<CustomsNotification> findAll() {
        return notificationStorage.findAll();
    }

    public CustomsNotification findById(Long id) {
        return notificationStorage.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found: " + id));
    }

    public void deleteNotification(Long id) {
        CustomsNotification notification = findById(id);
        if (notification.getStatus() != NotificationStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT notifications can be deleted");
        }
        notificationStorage.deleteById(id);
        log.info("Notification deleted: {}", id);
    }

    // ==================== Ausgehende Notifications ====================

    /**
     * Erstellt eine neue Notification mit LLEs.
     */
    public CustomsNotification createNotification(String receiverBpnl, List<Long> lleIds,
            Long requestId, String remarks) {
        log.info("Creating notification for receiver {} with {} LLEs", receiverBpnl, lleIds.size());

        CustomsNotification notification = CustomsNotification.createOutgoing(ownBpnl, receiverBpnl);
        notification.setOriginalRequestId(requestId);
        notification.setRemarks(remarks);

        // LLEs laden und als Items hinzufügen
        for (Long lleId : lleIds) {
            CustomsData lle = customsStorage.loadCustomsDataById(lleId);
            if (lle == null) {
                throw new IllegalArgumentException("LLE not found: " + lleId);
            }

            NotificationItem item = NotificationItem.fromCustomsData(lle);
            notification.addItem(item);
        }

        CustomsNotification saved = notificationStorage.save(notification);
        log.info("Notification created with ID: {} and {} items", saved.getId(), saved.getItemCount());

        return saved;
    }

    /**
     * Versendet eine Notification.
     */
    public CustomsNotification sendNotification(Long id) {
        CustomsNotification notification = findById(id);

        if (!notification.canBeSent()) {
            throw new IllegalStateException("Notification cannot be sent: " + notification.getStatus());
        }

        notification.setStatus(NotificationStatus.SENT);
        notification.getHeader().setSentDateTime(LocalDateTime.now());

        CustomsNotification saved = notificationStorage.save(notification);
        log.info("Notification sent: {}", id);

        // TODO: EDC-Integration - Notification über EDC versenden
        // edcService.sendNotification(saved);

        return saved;
    }

    public List<CustomsNotification> findOutgoingNotifications() {
        return notificationStorage.findByDirection(NotificationDirection.OUTGOING);
    }

    public List<CustomsNotification> findOutgoingNotificationsByStatus(NotificationStatus status) {
        return notificationStorage.findByDirectionAndStatus(NotificationDirection.OUTGOING, status);
    }

    // ==================== Eingehende Notifications ====================

    public List<CustomsNotification> findIncomingNotifications() {
        return notificationStorage.findByDirection(NotificationDirection.INCOMING);
    }

    public List<CustomsNotification> findIncomingNotificationsByStatus(NotificationStatus status) {
        return notificationStorage.findByDirectionAndStatus(NotificationDirection.INCOMING, status);
    }

    /**
     * Empfängt eine eingehende Notification (simuliert EDC-Empfang).
     */
    public CustomsNotification receiveNotification(CustomsNotification notification) {
        log.info("Receiving notification from {}", notification.getHeader().getSenderBpnl());

        notification.setDirection(NotificationDirection.INCOMING);
        notification.setStatus(NotificationStatus.RECEIVED);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());

        // Header-Daten anpassen
        notification.getHeader().setReceiverBpnl(ownBpnl);

        CustomsNotification saved = notificationStorage.save(notification);
        log.info("Incoming notification received with ID: {}", saved.getId());

        return saved;
    }

    /**
     * Markiert eine Notification als verarbeitet.
     */
    public CustomsNotification markAsProcessed(Long id) {
        CustomsNotification notification = findById(id);

        if (notification.getDirection() != NotificationDirection.INCOMING) {
            throw new IllegalStateException("Only incoming notifications can be processed");
        }

        notification.setStatus(NotificationStatus.PROCESSED);
        notification.setUpdatedAt(LocalDateTime.now());

        CustomsNotification saved = notificationStorage.save(notification);
        log.info("Notification marked as processed: {}", id);

        return saved;
    }

    // ==================== Item-Verwaltung ====================

    /**
     * Übernimmt ein Item in eine eigene LLE.
     */
    public void adoptItem(Long notificationId, Long itemId, Long snrId) {
        log.info("Adopting item {} from notification {}", itemId, notificationId);

        CustomsNotification notification = findById(notificationId);
        if (notification.getDirection() != NotificationDirection.INCOMING) {
            throw new IllegalStateException("Can only adopt from incoming notifications");
        }

        NotificationItem item = notificationStorage.findItemById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: " + itemId));

        if (item.isAdopted()) {
            throw new IllegalStateException("Item already adopted");
        }

        // TODO: Neue LLE aus Item erstellen
        // CustomsData newLle = createLleFromItem(item, snrId);
        // customsStorage.save(newLle);
        // item.setAdopted(true);
        // item.setAdoptedLleId(newLle.getId());

        item.setAdopted(true);
        item.setUpdatedAt(LocalDateTime.now());
        notificationStorage.saveItem(item);

        log.info("Item adopted: {}", itemId);
    }

    /**
     * Übernimmt alle Items einer Notification.
     */
    public void adoptAllItems(Long notificationId) {
        CustomsNotification notification = findById(notificationId);

        for (NotificationItem item : notification.getListOfItems()) {
            if (!item.isAdopted()) {
                adoptItem(notificationId, item.getId(), null);
            }
        }

        // Notification als verarbeitet markieren
        markAsProcessed(notificationId);
    }

    // ==================== Statistiken ====================

    public NotificationStats getStats() {
        List<CustomsNotification> outgoing = findOutgoingNotifications();
        List<CustomsNotification> incoming = findIncomingNotifications();

        return new NotificationStats(
                outgoing.size(),
                outgoing.stream().filter(n -> n.getStatus() == NotificationStatus.DRAFT).count(),
                outgoing.stream().filter(n -> n.getStatus() == NotificationStatus.SENT).count(),
                incoming.size(),
                incoming.stream().filter(n -> n.getStatus() == NotificationStatus.RECEIVED).count(),
                incoming.stream().filter(n -> n.getStatus() == NotificationStatus.PROCESSED).count(),
                notificationStorage.countUnprocessedIncoming()
        );
    }

    public record NotificationStats(
            long outgoingTotal,
            long outgoingDraft,
            long outgoingSent,
            long incomingTotal,
            long incomingReceived,
            long incomingProcessed,
            long incomingUnprocessed
    ) {}
}
