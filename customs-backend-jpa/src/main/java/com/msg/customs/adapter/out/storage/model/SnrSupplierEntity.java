package com.msg.customs.adapter.out.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Zwischentabelle für die n:m Beziehung zwischen MasterPart (SNR) und Supplier.
 * Implementiert FR-2.3 aus dem Lastenheft: Zuordnung SNR zu Lieferant.
 */
@Entity
@Table(name = "snr_supplier")
@Getter
@Setter
@NoArgsConstructor
public class SnrSupplierEntity {

	@EmbeddedId
	private SnrSupplierId id;

	@ManyToOne
	@MapsId("masterpartId")
	@JoinColumn(name = "masterpart_id")
	private MasterPartEntity masterpart;

	@ManyToOne
	@MapsId("supplierBpnl")
	@JoinColumn(name = "supplier_bpnl")
	private SupplierEntity supplier;

	/**
	 * Markiert den Hauptlieferanten für diese SNR.
	 * Pro SNR kann nur ein Lieferant als primär markiert sein.
	 */
	@Column(name = "is_primary")
	private boolean primary;

	/**
	 * Optionale Bemerkung zur Zuordnung.
	 */
	@Column(name = "note", length = 500)
	private String note;

	public SnrSupplierEntity(MasterPartEntity masterpart, SupplierEntity supplier) {
		this.masterpart = masterpart;
		this.supplier = supplier;
		this.id = new SnrSupplierId(masterpart.getId(), supplier.getBpnl());
	}
}
