package com.msg.customs.adapter.in.web.model;

import lombok.Data;

/**
 * DTO für die Verknüpfung zwischen SNR und Lieferant.
 */
@Data
public class SnrSupplierLinkDto {

	private Long masterpartId;
	private String catenaXId;
	private String supplierBpnl;
	private String supplierName;
	private boolean primary;
	private String note;
}
