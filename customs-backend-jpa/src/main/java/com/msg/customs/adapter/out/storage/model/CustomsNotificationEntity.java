package com.msg.customs.adapter.out.storage.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.msg.customs.model.NotificationDirection;
import com.msg.customs.model.NotificationStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity für CustomsNotification.
 * Repräsentiert eine Benachrichtigung mit LLE-Daten (listOfItems).
 *
 * Basiert auf dem Catena-X Industry Core CustomsNotification Standard.
 */
@Entity
@Table(name = "customs_notification")
@Getter
@Setter
@NoArgsConstructor
public class CustomsNotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Notification Header mit Metadaten.
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "header_id")
    private NotificationHeaderEntity header;

    /**
     * Liste der LLE-Items in dieser Notification.
     */
    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationItemEntity> listOfItems = new ArrayList<>();

    /**
     * Richtung der Notification (ein- oder ausgehend).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationDirection direction;

    /**
     * Status der Notification.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status = NotificationStatus.DRAFT;

    /**
     * Verknüpfung zum Lieferanten (bei eingehenden Notifications).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_bpnl")
    private SupplierEntity supplier;

    /**
     * Optional: Verknüpfung zur ursprünglichen Anfrage.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private CustomsRequestEntity originalRequest;

    /**
     * Bemerkungen / Notizen.
     */
    @Column(length = 2000)
    private String remarks;

    /**
     * Zeitpunkt der Erstellung.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Zeitpunkt der letzten Aktualisierung.
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = NotificationStatus.DRAFT;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ==================== Helper Methods ====================

    /**
     * Fügt ein Item zur Notification hinzu.
     */
    public void addItem(NotificationItemEntity item) {
        listOfItems.add(item);
        item.setNotification(this);
    }

    /**
     * Entfernt ein Item aus der Notification.
     */
    public void removeItem(NotificationItemEntity item) {
        listOfItems.remove(item);
        item.setNotification(null);
    }

    /**
     * Markiert die Notification als versendet.
     */
    public void markAsSent() {
        this.status = NotificationStatus.SENT;
        if (header != null) {
            header.setSentDateTime(LocalDateTime.now());
        }
    }

    /**
     * Markiert die Notification als verarbeitet.
     */
    public void markAsProcessed() {
        this.status = NotificationStatus.PROCESSED;
    }

    /**
     * Prüft ob die Notification Items enthält.
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
}
