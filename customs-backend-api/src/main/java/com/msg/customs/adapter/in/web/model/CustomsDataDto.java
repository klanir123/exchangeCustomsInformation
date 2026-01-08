package com.msg.customs.adapter.in.web.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msg.customs.model.CustomsTypeEnum;
import com.msg.customs.model.LleStatus;
import com.msg.customs.model.RequestStatusEnum;

import lombok.Data;

/**
 * Simple class to exchange customs data with ui.
 * Erweitert für Phase 3: LLE-Erweiterungen.
 */
@Data
public class CustomsDataDto {

	private Long id;

	@JsonProperty("catenaxId")
    private String catenaxId;

    private RequestStatusEnum requestStatus;

    private String customerPartNumber;
    private String customerPartName;

    private String manufacturerPartNumber;
    private String manufacturerPartName;

    private CustomsTypeEnum type;

    private LocalDate confirmationDate;

    private LocalDate validFrom;

    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String contactJobTitle;

    private String country;

    private String federalState;

    private List<String> originInformation;

    private String prefferedStatus;

    private String tracedWorthValue;
    private String tracedWorthCurrency;

    // ==================== Phase 3: LLE-Erweiterungen ====================

    /**
     * Verknüpfter Lieferant (BPNL).
     */
    private String supplierBpnl;

    /**
     * Name des verknüpften Lieferanten (nur Ausgabe).
     */
    private String supplierName;

    /**
     * Gültigkeitsdatum (berechnet: validFrom + 365 Tage).
     */
    private LocalDate validUntil;

    /**
     * Status der LLE.
     */
    private LleStatus lleStatus;

    /**
     * ID der Vorgänger-LLE bei Erneuerung.
     */
    private Long predecessorId;

    /**
     * Dateiname des PDF-Dokuments (falls vorhanden).
     */
    private String pdfFileName;

    /**
     * Zeitpunkt des PDF-Uploads.
     */
    private LocalDateTime pdfUploadedAt;

    /**
     * Gibt an, ob ein PDF vorhanden ist.
     */
    private boolean hasPdf;
}
