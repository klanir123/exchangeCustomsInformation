package com.msg.customs.adapter.out.storage.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity für NotificationItem.
 * Repräsentiert ein einzelnes LLE-Element innerhalb einer CustomsNotification.
 *
 * Entspricht dem "listOfItems[]" Array in der CustomsNotification.
 */
@Entity
@Table(name = "notification_item")
@Getter
@Setter
@NoArgsConstructor
public class NotificationItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Zugehörige Notification.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id", nullable = false)
    private CustomsNotificationEntity notification;

    // ==================== customsInformation ====================

    /**
     * Catena-X ID des Teils.
     */
    @Column(length = 100)
    private String catenaXId;

    /**
     * Typ der Erklärung (z.B. "InitialRequest", "AnnualRequest").
     */
    @Column(length = 50)
    private String type;

    /**
     * Bestätigungsdatum.
     */
    private LocalDate confirmationDate;

    /**
     * Gültig ab.
     */
    private LocalDate validFrom;

    /**
     * Gültig bis.
     */
    private LocalDate validUntil;

    /**
     * Ursprungsland (kommerziel).
     */
    @Column(length = 100)
    private String commercialCountryOfOrigin;

    /**
     * Bundesland/Region.
     */
    @Column(length = 100)
    private String federalState;

    /**
     * Präferenzstatus (z.B. "Y-Certified", "N-NotCertified").
     */
    @Column(length = 50)
    private String preferredStatus;

    /**
     * Status der LLE (Completed / Not-Completed).
     */
    @Column(length = 20)
    private String completionStatus;

    // ==================== tracedWorth ====================

    /**
     * Handelswert.
     */
    @Column(length = 50)
    private String tracedWorthValue;

    /**
     * Währung des Handelswerts.
     */
    @Column(length = 3)
    private String tracedWorthCurrency;

    // ==================== contactInformation ====================

    /**
     * Kontaktperson.
     */
    @Column(length = 255)
    private String contactPerson;

    /**
     * Telefonnummer.
     */
    @Column(length = 50)
    private String phoneNumber;

    /**
     * E-Mail.
     */
    @Column(length = 255)
    private String email;

    /**
     * Job-Titel.
     */
    @Column(length = 100)
    private String jobTitle;

    // ==================== originInformation ====================

    /**
     * Handelsabkommen (kommasepariert, z.B. "EU-MX,EU-CA").
     */
    @Column(length = 500)
    private String tradeRelations;

    // ==================== partData ====================

    /**
     * Kundenseitige Teilenummer (SNR).
     */
    @Column(length = 100)
    private String customerPartId;

    /**
     * Herstellerseitige Teilenummer.
     */
    @Column(length = 100)
    private String manufacturerPartId;

    /**
     * Teilename beim Kunden.
     */
    @Column(length = 255)
    private String nameAtCustomer;

    /**
     * Teilename beim Hersteller.
     */
    @Column(length = 255)
    private String nameAtManufacturer;

    // ==================== Metadaten ====================

    /**
     * Verknüpfung zur originalen LLE (falls aus eigenen Daten erstellt).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_lle_id")
    private CustomsEntity sourceLle;

    /**
     * Wurde dieses Item bereits in eine eigene LLE übernommen?
     */
    private boolean adopted = false;

    /**
     * ID der erstellten LLE nach Übernahme.
     */
    private Long adoptedLleId;

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
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ==================== Helper Methods ====================

    /**
     * Prüft ob der Status "Completed" ist.
     */
    public boolean isCompleted() {
        return "Completed".equalsIgnoreCase(completionStatus);
    }

    /**
     * Prüft ob das Item präferenzberechtigt ist.
     */
    public boolean isPreferential() {
        return preferredStatus != null && preferredStatus.startsWith("Y");
    }

    /**
     * Markiert das Item als übernommen.
     */
    public void markAsAdopted(Long lleId) {
        this.adopted = true;
        this.adoptedLleId = lleId;
    }
}
