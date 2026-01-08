package com.msg.customs.model;

/**
 * Status einer Langzeitlieferantenerklärung (LLE).
 * Implementiert FR-3.x aus dem Lastenheft.
 */
public enum LleStatus {
	Draft("Entwurf"),
	Active("Aktiv"),
	ExpiringSoon("Bald ablaufend"),  // < 60 Tage
	Critical("Kritisch"),             // < 30 Tage
	Expired("Abgelaufen");

	private final String displayName;

	LleStatus(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Berechnet den Status basierend auf den verbleibenden Tagen.
	 * @param daysRemaining Tage bis zum Ablauf
	 * @return Der berechnete Status
	 */
	public static LleStatus fromDaysRemaining(long daysRemaining) {
		if (daysRemaining < 0) {
			return Expired;
		} else if (daysRemaining < 30) {
			return Critical;
		} else if (daysRemaining < 60) {
			return ExpiringSoon;
		} else {
			return Active;
		}
	}
}
