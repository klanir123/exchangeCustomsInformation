package com.msg.customs.adapter.out.storage.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;

import com.msg.customs.adapter.out.storage.converter.CustomsTerritoryConverter;
import com.msg.customs.adapter.out.storage.converter.FederalStateConverter;
import com.msg.customs.model.CertifiedEnum;
import com.msg.customs.model.CustomsTerritory;
import com.msg.customs.model.CustomsTypeEnum;
import com.msg.customs.model.FederalState;
import com.msg.customs.model.LleStatus;
import com.msg.customs.model.RequestStatusEnum;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Basic;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Database representation of customs data (LLE - Langzeitlieferantenerklärung).
 * Erweitert für Phase 3: Gültigkeit, Status, Lieferant, PDF.
 */
@Getter
@Setter
@ToString(exclude = {"pdfDocument", "predecessor"})
@Entity
@Table(name = "customs")
public class CustomsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "catenax_id", nullable = false, unique = true)
    private String catenaxId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "request_status", nullable = false)
    private RequestStatusEnum requestStatus;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "number", column = @Column(name = "customer_part_number")),
        @AttributeOverride(name = "name", column = @Column(name = "customer_part_name"))
    })
    private PartEntity customerPart;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "number", column = @Column(name = "manufacturer_part_number")),
        @AttributeOverride(name = "name", column = @Column(name = "manufacturer_part_name"))
    })
    private PartEntity manufacturePart;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CustomsTypeEnum type;

    @Convert(converter = LocalDateConverter.class)
    @Column(name = "confirmation_date")
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate confirmationDate;

    @Convert(converter = LocalDateConverter.class)
    @Column(name = "valid_from", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate validFrom;

    @Embedded
    private ContactEntity contact;

    @Convert(converter = CustomsTerritoryConverter.class)
    @Column(name = "country", nullable = false)
    private CustomsTerritory country;

    @Convert(converter = FederalStateConverter.class)
    @Column(name = "federal_state")
    private FederalState federalState;

    @ElementCollection
    @CollectionTable(name = "customs_origin_information", joinColumns = @JoinColumn(name = "customs_id"))
    @Column(name = "origin_information")
    private List<String> originInformation;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_status")
    private CertifiedEnum preferredStatus;

    @Embedded
    private TracedWorthEntity tracedWorth;

    // ==================== Phase 3: LLE-Erweiterungen ====================

    /**
     * Verknüpfung mit dem Lieferanten.
     * FR-3.1: LLE wird einem Lieferanten zugeordnet.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_bpnl")
    private SupplierEntity supplier;

    /**
     * Gültigkeitsdatum (berechnet: validFrom + 365 Tage).
     * FR-3.2: Automatische Berechnung der Gültigkeit.
     */
    @Convert(converter = LocalDateConverter.class)
    @Column(name = "valid_until")
    private LocalDate validUntil;

    /**
     * Status der LLE basierend auf Gültigkeit.
     * FR-3.3: Status-Überwachung (Draft, Active, ExpiringSoon, Critical, Expired).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "lle_status")
    private LleStatus lleStatus;

    /**
     * Vorgänger-LLE bei Erneuerung.
     * FR-3.7: Verkettung bei LLE-Erneuerung.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "predecessor_id")
    private CustomsEntity predecessor;

    /**
     * PDF-Dokument der LLE.
     * FR-3.5: PDF-Speicherung.
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "pdf_document")
    private byte[] pdfDocument;

    /**
     * Dateiname des PDF-Dokuments.
     */
    @Column(name = "pdf_file_name", length = 255)
    private String pdfFileName;

    /**
     * Zeitpunkt des PDF-Uploads.
     */
    @Column(name = "pdf_uploaded_at")
    private LocalDateTime pdfUploadedAt;

    // ==================== Lifecycle Callbacks ====================

    @PrePersist
    public void prePersist() {
        if (confirmationDate == null) {
            confirmationDate = LocalDate.now();
        }
        calculateValidityAndStatus();
    }

    @PreUpdate
    public void preUpdate() {
        calculateValidityAndStatus();
    }

    /**
     * Berechnet validUntil und aktualisiert den LLE-Status.
     * FR-3.3: Automatische Status-Berechnung basierend auf Gültigkeit.
     */
    private void calculateValidityAndStatus() {
        // validUntil berechnen (validFrom + 365 Tage)
        if (validFrom != null) {
            this.validUntil = validFrom.plusDays(365);
        }

        // LLE-Status berechnen
        if (lleStatus == LleStatus.Draft) {
            // Draft-Status bleibt erhalten bis explizit geändert
            return;
        }

        if (validUntil != null) {
            long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), validUntil);
            this.lleStatus = LleStatus.fromDaysRemaining(daysRemaining);
        }
    }

    /**
     * Aktiviert die LLE (wechselt von Draft zu Active).
     */
    public void activate() {
        if (this.lleStatus == LleStatus.Draft) {
            calculateValidityAndStatus();
            if (validUntil != null) {
                long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), validUntil);
                this.lleStatus = LleStatus.fromDaysRemaining(daysRemaining);
            } else {
                this.lleStatus = LleStatus.Active;
            }
        }
    }
}