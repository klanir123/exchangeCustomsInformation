package com.msg.customs.application.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.msg.customs.model.CertifiedEnum;
import com.msg.customs.model.CustomsTerritory;
import com.msg.customs.model.CustomsTypeEnum;
import com.msg.customs.model.FederalState;
import com.msg.customs.model.LleStatus;
import com.msg.customs.model.RequestStatusEnum;

import lombok.Data;

/**
 * Domain Model für LLE (Langzeitlieferantenerklärung).
 * Erweitert für Phase 3: Gültigkeit, Status, Lieferant, PDF.
 */
@Data
public class CustomsData {

	// internal id to store the entity
	private Long id;

	private RequestStatusEnum requestStatus;

	private String catenaxId;

	private Part customerPart;

	private Part manufacturerPart;

	// required
	private CustomsTypeEnum type;

	// internal date to store the date of the creation
	private LocalDate confirmationDate;

	// required
	private LocalDate validFrom;

	// required
	private ContactData contact;

	// required
	private CustomsTerritory country;

	// country != DE -> disabled, = 99
	private FederalState federalState;

	// country == EU -> multi selection possible
	private List<String> originInformation;

	// country != EU -> disabled, = N-NotCertified
	private CertifiedEnum preferredStatus;

	private TracedWorth tracedWorth;

	// ==================== Phase 3: LLE-Erweiterungen ====================

	/**
	 * Verknüpfter Lieferant (BPNL).
	 */
	private String supplierBpnl;

	/**
	 * Name des verknüpften Lieferanten.
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
	 * Dateiname des PDF-Dokuments.
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
