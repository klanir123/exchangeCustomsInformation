package com.msg.customs.application.domain.model;

import lombok.Data;

/**
 * Domain Model für die Verknüpfung zwischen SNR und Lieferant.
 */
@Data
public class SnrSupplierLink {

	private Long masterpartId;
	private String catenaXId;
	private String supplierBpnl;
	private String supplierName;
	private boolean primary;
	private String note;
}
