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
import org.springframework.web.bind.annotation.RequestParam;

import com.msg.customs.adapter.in.web.model.SupplierContactDto;
import com.msg.customs.adapter.in.web.model.SupplierDto;
import com.msg.customs.model.SupplierStatus;

/**
 * API für die Lieferantenverwaltung.
 * Gemäß Lastenheft Epic 1: Lieferantenverwaltung.
 */
@RequestMapping(value = "/suppliers", produces = MediaType.APPLICATION_JSON_VALUE)
public interface SupplierAPI {

	// ==================== Lieferanten CRUD ====================

	/**
	 * FR-1.2: Alle Lieferanten abrufen.
	 * @return Liste aller Lieferanten
	 */
	@GetMapping
	ResponseEntity<List<SupplierDto>> getAllSuppliers();

	/**
	 * FR-1.2: Nur aktive Lieferanten abrufen (für Dropdown-Auswahl).
	 * @return Liste aktiver Lieferanten
	 */
	@GetMapping(value = "/active")
	ResponseEntity<List<SupplierDto>> getActiveSuppliers();

	/**
	 * FR-1.2: Lieferant nach BPNL abrufen.
	 * @param bpnl Business Partner Number Legal
	 * @return Lieferant mit allen Details
	 */
	@GetMapping(value = "/{bpnl}")
	ResponseEntity<SupplierDto> getSupplierByBpnl(@PathVariable String bpnl);

	/**
	 * FR-1.1: Neuen Lieferanten anlegen.
	 * BPNL wird validiert (Format: BPNL + 12 alphanumerische Zeichen).
	 * Duplikatprüfung auf BPNL.
	 * @param supplier Lieferantendaten
	 * @return HTTP Status
	 */
	@PostMapping
	ResponseEntity<Void> createSupplier(@RequestBody SupplierDto supplier);

	/**
	 * FR-1.3: Lieferant bearbeiten.
	 * BPNL ist nicht änderbar (fachlicher Schlüssel).
	 * @param bpnl Business Partner Number Legal
	 * @param supplier Aktualisierte Lieferantendaten
	 * @return HTTP Status
	 */
	@PutMapping(value = "/{bpnl}")
	ResponseEntity<Void> updateSupplier(@PathVariable String bpnl, @RequestBody SupplierDto supplier);

	/**
	 * FR-1.4: Lieferant deaktivieren/sperren.
	 * @param bpnl Business Partner Number Legal
	 * @param status Neuer Status (Inactive oder Blocked)
	 * @return HTTP Status
	 */
	@PatchMapping(value = "/{bpnl}/status")
	ResponseEntity<Void> updateSupplierStatus(@PathVariable String bpnl, @RequestBody SupplierStatus status);

	/**
	 * Lieferant löschen (nur wenn keine verknüpften SNRs/LLEs).
	 * @param bpnl Business Partner Number Legal
	 * @return HTTP Status
	 */
	@DeleteMapping(value = "/{bpnl}")
	ResponseEntity<Void> deleteSupplier(@PathVariable String bpnl);

	// ==================== Suche ====================

	/**
	 * FR-1.2: Lieferanten suchen.
	 * Suchfunktion nach BPNL (exakte Suche) oder Name (Teilsuche).
	 * @param query Suchbegriff (BPNL oder Name)
	 * @return Liste gefundener Lieferanten
	 */
	@GetMapping(value = "/search")
	ResponseEntity<List<SupplierDto>> searchSuppliers(@RequestParam String query);

	/**
	 * Prüft ob eine BPNL bereits existiert.
	 * @param bpnl Business Partner Number Legal
	 * @return true wenn BPNL existiert
	 */
	@GetMapping(value = "/exists/{bpnl}")
	ResponseEntity<Boolean> existsByBpnl(@PathVariable String bpnl);

	// ==================== Kontakte ====================

	/**
	 * FR-1.6: Alle Kontakte eines Lieferanten abrufen.
	 * @param bpnl Business Partner Number Legal
	 * @return Liste der Kontakte
	 */
	@GetMapping(value = "/{bpnl}/contacts")
	ResponseEntity<List<SupplierContactDto>> getSupplierContacts(@PathVariable String bpnl);

	/**
	 * FR-1.6: Kontakt zu Lieferant hinzufügen.
	 * @param bpnl Business Partner Number Legal
	 * @param contact Kontaktdaten
	 * @return HTTP Status
	 */
	@PostMapping(value = "/{bpnl}/contacts")
	ResponseEntity<Void> addSupplierContact(@PathVariable String bpnl, @RequestBody SupplierContactDto contact);

	/**
	 * FR-1.6: Kontakt aktualisieren.
	 * @param bpnl Business Partner Number Legal
	 * @param contactId Kontakt-ID
	 * @param contact Aktualisierte Kontaktdaten
	 * @return HTTP Status
	 */
	@PutMapping(value = "/{bpnl}/contacts/{contactId}")
	ResponseEntity<Void> updateSupplierContact(
		@PathVariable String bpnl,
		@PathVariable Long contactId,
		@RequestBody SupplierContactDto contact);

	/**
	 * FR-1.6: Kontakt löschen.
	 * @param bpnl Business Partner Number Legal
	 * @param contactId Kontakt-ID
	 * @return HTTP Status
	 */
	@DeleteMapping(value = "/{bpnl}/contacts/{contactId}")
	ResponseEntity<Void> deleteSupplierContact(@PathVariable String bpnl, @PathVariable Long contactId);

	/**
	 * FR-1.6: Kontakt als primären Ansprechpartner markieren.
	 * @param bpnl Business Partner Number Legal
	 * @param contactId Kontakt-ID
	 * @return HTTP Status
	 */
	@PatchMapping(value = "/{bpnl}/contacts/{contactId}/primary")
	ResponseEntity<Void> setContactAsPrimary(@PathVariable String bpnl, @PathVariable Long contactId);
}
