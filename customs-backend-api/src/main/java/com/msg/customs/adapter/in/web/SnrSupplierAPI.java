package com.msg.customs.adapter.in.web;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.msg.customs.adapter.in.web.model.SnrSupplierLinkDto;

/**
 * API für die SNR-Lieferanten-Verknüpfungen.
 * Implementiert FR-2.3 aus dem Lastenheft: Zuordnung SNR zu Lieferant.
 */
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public interface SnrSupplierAPI {

	// ==================== SNR -> Lieferanten ====================

	/**
	 * FR-2.3: Alle Lieferanten für eine SNR abrufen.
	 * @param masterpartId ID des Masterparts (SNR)
	 * @return Liste aller verknüpften Lieferanten
	 */
	@GetMapping(value = "/customs/parts/{masterpartId}/suppliers")
	ResponseEntity<List<SnrSupplierLinkDto>> getSuppliersForSnr(@PathVariable Long masterpartId);

	/**
	 * FR-2.3: Lieferant zu SNR hinzufügen.
	 * @param masterpartId ID des Masterparts (SNR)
	 * @param link Verknüpfungsdaten (supplierBpnl, isPrimary, note)
	 * @return HTTP Status
	 */
	@PostMapping(value = "/customs/parts/{masterpartId}/suppliers")
	ResponseEntity<Void> addSupplierToSnr(
		@PathVariable Long masterpartId,
		@RequestBody SnrSupplierLinkDto link);

	/**
	 * FR-2.3: Lieferant von SNR entfernen.
	 * @param masterpartId ID des Masterparts (SNR)
	 * @param supplierBpnl BPNL des Lieferanten
	 * @return HTTP Status
	 */
	@DeleteMapping(value = "/customs/parts/{masterpartId}/suppliers/{supplierBpnl}")
	ResponseEntity<Void> removeSupplierFromSnr(
		@PathVariable Long masterpartId,
		@PathVariable String supplierBpnl);

	/**
	 * FR-2.3: Lieferant als Hauptlieferant markieren.
	 * @param masterpartId ID des Masterparts (SNR)
	 * @param supplierBpnl BPNL des Lieferanten
	 * @return HTTP Status
	 */
	@PatchMapping(value = "/customs/parts/{masterpartId}/suppliers/{supplierBpnl}/primary")
	ResponseEntity<Void> setPrimarySupplier(
		@PathVariable Long masterpartId,
		@PathVariable String supplierBpnl);

	/**
	 * FR-2.3: Notiz der Verknüpfung aktualisieren.
	 * @param masterpartId ID des Masterparts (SNR)
	 * @param supplierBpnl BPNL des Lieferanten
	 * @param link Verknüpfungsdaten mit neuer Notiz
	 * @return HTTP Status
	 */
	@PutMapping(value = "/customs/parts/{masterpartId}/suppliers/{supplierBpnl}")
	ResponseEntity<Void> updateSnrSupplierLink(
		@PathVariable Long masterpartId,
		@PathVariable String supplierBpnl,
		@RequestBody SnrSupplierLinkDto link);

	// ==================== Lieferant -> SNRs ====================

	/**
	 * Alle SNRs für einen Lieferanten abrufen.
	 * @param supplierBpnl BPNL des Lieferanten
	 * @return Liste aller verknüpften SNRs
	 */
	@GetMapping(value = "/suppliers/{supplierBpnl}/snrs")
	ResponseEntity<List<SnrSupplierLinkDto>> getSnrsForSupplier(@PathVariable String supplierBpnl);

	/**
	 * Prüft ob eine Verknüpfung existiert.
	 * @param masterpartId ID des Masterparts (SNR)
	 * @param supplierBpnl BPNL des Lieferanten
	 * @return true wenn Verknüpfung existiert
	 */
	@GetMapping(value = "/customs/parts/{masterpartId}/suppliers/{supplierBpnl}/exists")
	ResponseEntity<Boolean> linkExists(
		@PathVariable Long masterpartId,
		@PathVariable String supplierBpnl);
}
