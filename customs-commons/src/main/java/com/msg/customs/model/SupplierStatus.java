package com.msg.customs.model;

public enum SupplierStatus {
	Active("Aktiv"),
	Inactive("Inaktiv"),
	Blocked("Gesperrt");

	private final String displayName;

	SupplierStatus(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
