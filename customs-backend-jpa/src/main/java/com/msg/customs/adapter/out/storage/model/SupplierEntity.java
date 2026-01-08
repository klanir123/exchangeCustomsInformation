package com.msg.customs.adapter.out.storage.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.msg.customs.model.SupplierStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Database representation of a Supplier (Lieferant).
 * BPNL (Business Partner Number Legal) is the primary key as defined in the requirements.
 */
@Getter
@Setter
@ToString(exclude = {"contacts", "snrLinks"})
@Entity
@Table(name = "supplier")
public class SupplierEntity {

    /**
     * BPNL - Business Partner Number Legal.
     * Format: "BPNL" + 12 alphanumerische Zeichen (z.B. BPNL000000000AAA)
     * Fachlicher Schlüssel gemäß Lastenheft.
     */
    @Id
    @Column(name = "bpnl", length = 16, nullable = false)
    private String bpnl;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "street")
    private String street;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "city", length = 100)
    private String city;

    /**
     * Land gemäß ISO 3166-1 alpha-2 (z.B. "DE", "FR", "US")
     */
    @Column(name = "country", length = 2)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SupplierStatus status = SupplierStatus.Active;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<SupplierContactEntity> contacts = new ArrayList<>();

    /**
     * Verknüpfungen zu Sachnummern (SNRs).
     * FR-2.3: Ein Lieferant kann mehreren Produkten zugeordnet sein.
     */
    @OneToMany(mappedBy = "supplier")
    private List<SnrSupplierEntity> snrLinks = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Fügt einen Kontakt zum Lieferanten hinzu.
     */
    public void addContact(SupplierContactEntity contact) {
        contacts.add(contact);
        contact.setSupplier(this);
    }

    /**
     * Entfernt einen Kontakt vom Lieferanten.
     */
    public void removeContact(SupplierContactEntity contact) {
        contacts.remove(contact);
        contact.setSupplier(null);
    }
}
