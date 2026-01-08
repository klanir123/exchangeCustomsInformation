package com.msg.customs.adapter.out.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Database representation of a Supplier Contact (Lieferanten-Kontakt).
 * Gemäß Lastenheft FR-1.6: Mehrere Kontakte pro Lieferant möglich.
 */
@Getter
@Setter
@ToString(exclude = "supplier")
@Entity
@Table(name = "supplier_contact")
public class SupplierContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_bpnl", nullable = false)
    private SupplierEntity supplier;

    /**
     * Name des Ansprechpartners (contactPerson gemäß Aspektmodell)
     */
    @Column(name = "contact_person", nullable = false)
    private String contactPerson;

    /**
     * Telefonnummer (phoneNumber gemäß Aspektmodell)
     */
    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    /**
     * E-Mail-Adresse (email gemäß Aspektmodell)
     */
    @Column(name = "email")
    private String email;

    /**
     * Position/Funktion (jobTitle gemäß Aspektmodell)
     */
    @Column(name = "job_title", length = 100)
    private String jobTitle;

    /**
     * Kennzeichnung des Hauptansprechpartners für Zollfragen (FR-1.6)
     */
    @Column(name = "is_primary")
    private boolean primary = false;

    /**
     * Kontakt kann als "bevorzugt für CustomsNotification" markiert werden (FR-1.6)
     */
    @Column(name = "preferred_for_notifications")
    private boolean preferredForNotifications = false;
}
