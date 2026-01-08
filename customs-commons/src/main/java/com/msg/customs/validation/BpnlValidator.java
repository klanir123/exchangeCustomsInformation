package com.msg.customs.validation;

import java.util.regex.Pattern;

/**
 * Validator für Business Partner Number Legal (BPNL).
 * Format: "BPNL" + 12 alphanumerische Zeichen (Großbuchstaben und Ziffern)
 * Beispiel: BPNL000000000AAA
 */
public class BpnlValidator {

	private static final Pattern BPNL_PATTERN = Pattern.compile("^BPNL[A-Z0-9]{12}$");

	/**
	 * Prüft ob eine BPNL gültig ist.
	 * @param bpnl Die zu prüfende BPNL
	 * @return true wenn gültig, false sonst
	 */
	public static boolean isValid(String bpnl) {
		if (bpnl == null || bpnl.isEmpty()) {
			return false;
		}
		return BPNL_PATTERN.matcher(bpnl).matches();
	}

	/**
	 * Validiert eine BPNL und wirft eine Exception wenn ungültig.
	 * @param bpnl Die zu prüfende BPNL
	 * @throws IllegalArgumentException wenn die BPNL ungültig ist
	 */
	public static void validate(String bpnl) {
		if (!isValid(bpnl)) {
			throw new IllegalArgumentException(
				"Ungültige BPNL: '" + bpnl + "'. Format muss 'BPNL' + 12 alphanumerische Zeichen sein (z.B. BPNL000000000AAA)");
		}
	}

	/**
	 * Normalisiert eine BPNL (Großbuchstaben, trimmen).
	 * @param bpnl Die zu normalisierende BPNL
	 * @return Die normalisierte BPNL
	 */
	public static String normalize(String bpnl) {
		if (bpnl == null) {
			return null;
		}
		return bpnl.trim().toUpperCase();
	}
}
