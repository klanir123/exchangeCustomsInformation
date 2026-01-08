package com.msg.customs.adapter.out.storage.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Composite Primary Key für die n:m Beziehung zwischen SNR und Supplier.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SnrSupplierId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "masterpart_id")
	private Long masterpartId;

	@Column(name = "supplier_bpnl", length = 16)
	private String supplierBpnl;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SnrSupplierId that = (SnrSupplierId) o;
		return Objects.equals(masterpartId, that.masterpartId) &&
			   Objects.equals(supplierBpnl, that.supplierBpnl);
	}

	@Override
	public int hashCode() {
		return Objects.hash(masterpartId, supplierBpnl);
	}
}
