package com.msg.customs.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.msg.customs.application.domain.model.Supplier;
import com.msg.customs.application.domain.model.SupplierContact;
import com.msg.customs.application.port.in.LoadSupplierListUseCase;
import com.msg.customs.application.port.in.SaveSupplierUseCase;
import com.msg.customs.application.port.out.SupplierStorageAdapter;
import com.msg.customs.model.SupplierStatus;
import com.msg.customs.validation.BpnlValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service für die Lieferantenverwaltung.
 * Implementiert Epic 1 aus dem Lastenheft.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierService implements LoadSupplierListUseCase, SaveSupplierUseCase {

	private final SupplierStorageAdapter supplierStorage;

	// ==================== LoadSupplierListUseCase ====================

	@Override
	public List<Supplier> loadAllSuppliers() {
		log.debug("Loading all suppliers");
		return supplierStorage.findAll();
	}

	@Override
	public List<Supplier> loadSuppliersByStatus(SupplierStatus status) {
		log.debug("Loading suppliers by status: {}", status);
		return supplierStorage.findByStatus(status);
	}

	@Override
	public List<Supplier> loadActiveSuppliers() {
		log.debug("Loading active suppliers");
		return supplierStorage.findByStatus(SupplierStatus.Active);
	}

	@Override
	public Supplier loadSupplierByBpnl(String bpnl) {
		log.debug("Loading supplier by BPNL: {}", bpnl);
		return supplierStorage.findByBpnl(bpnl)
			.orElseThrow(() -> new IllegalArgumentException("Lieferant mit BPNL " + bpnl + " nicht gefunden"));
	}

	@Override
	public List<Supplier> searchSuppliers(String query) {
		log.debug("Searching suppliers with query: {}", query);
		return supplierStorage.searchByBpnlOrName(query);
	}

	@Override
	public boolean existsByBpnl(String bpnl) {
		return supplierStorage.existsByBpnl(bpnl);
	}

	// ==================== SaveSupplierUseCase ====================

	@Override
	public void createSupplier(Supplier supplier) {
		log.info("Creating new supplier with BPNL: {}", supplier.getBpnl());

		// FR-1.1: BPNL-Format validieren
		String normalizedBpnl = BpnlValidator.normalize(supplier.getBpnl());
		BpnlValidator.validate(normalizedBpnl);
		supplier.setBpnl(normalizedBpnl);

		// FR-1.1: Duplikatprüfung
		if (supplierStorage.existsByBpnl(normalizedBpnl)) {
			throw new IllegalArgumentException("Lieferant mit BPNL " + normalizedBpnl + " existiert bereits");
		}

		// Status setzen falls nicht vorhanden
		if (supplier.getStatus() == null) {
			supplier.setStatus(SupplierStatus.Active);
		}

		supplierStorage.save(supplier);
		log.info("Supplier created successfully: {}", normalizedBpnl);
	}

	@Override
	public void updateSupplier(String bpnl, Supplier supplier) {
		log.info("Updating supplier with BPNL: {}", bpnl);

		// Prüfen ob Lieferant existiert
		Supplier existing = loadSupplierByBpnl(bpnl);

		// FR-1.3: BPNL ist nicht änderbar
		supplier.setBpnl(existing.getBpnl());

		supplierStorage.save(supplier);
		log.info("Supplier updated successfully: {}", bpnl);
	}

	@Override
	public void updateSupplierStatus(String bpnl, SupplierStatus status) {
		log.info("Updating supplier status: {} -> {}", bpnl, status);

		Supplier supplier = loadSupplierByBpnl(bpnl);

		// FR-1.4: Warnung bei offenen Anfragen könnte hier geprüft werden
		// TODO: Prüfung auf offene RequestCustomsInformation

		supplier.setStatus(status);
		supplierStorage.save(supplier);
		log.info("Supplier status updated: {} -> {}", bpnl, status);
	}

	@Override
	public void deleteSupplier(String bpnl) {
		log.info("Deleting supplier with BPNL: {}", bpnl);

		// Prüfen ob Lieferant existiert
		loadSupplierByBpnl(bpnl);

		// TODO: Prüfung auf verknüpfte SNRs/LLEs
		// if (hasLinkedSnrsOrLles(bpnl)) {
		//     throw new IllegalStateException("Lieferant hat noch verknüpfte SNRs oder LLEs");
		// }

		supplierStorage.deleteByBpnl(bpnl);
		log.info("Supplier deleted: {}", bpnl);
	}

	// ==================== Kontakt-Verwaltung ====================

	@Override
	public void addContact(String bpnl, SupplierContact contact) {
		log.info("Adding contact to supplier: {}", bpnl);

		// Prüfen ob Lieferant existiert
		loadSupplierByBpnl(bpnl);

		supplierStorage.saveContact(bpnl, contact);
		log.info("Contact added to supplier: {}", bpnl);
	}

	@Override
	public void updateContact(String bpnl, Long contactId, SupplierContact contact) {
		log.info("Updating contact {} for supplier: {}", contactId, bpnl);

		// Prüfen ob Lieferant existiert
		loadSupplierByBpnl(bpnl);

		contact.setId(contactId);
		supplierStorage.saveContact(bpnl, contact);
		log.info("Contact updated: {}", contactId);
	}

	@Override
	public void deleteContact(String bpnl, Long contactId) {
		log.info("Deleting contact {} from supplier: {}", contactId, bpnl);

		// Prüfen ob Lieferant existiert
		loadSupplierByBpnl(bpnl);

		supplierStorage.deleteContact(bpnl, contactId);
		log.info("Contact deleted: {}", contactId);
	}

	@Override
	public void setContactAsPrimary(String bpnl, Long contactId) {
		log.info("Setting contact {} as primary for supplier: {}", contactId, bpnl);

		Supplier supplier = loadSupplierByBpnl(bpnl);

		// Alle anderen Kontakte auf isPrimary = false setzen
		for (SupplierContact contact : supplier.getContacts()) {
			contact.setPrimary(contact.getId().equals(contactId));
			supplierStorage.saveContact(bpnl, contact);
		}

		log.info("Contact {} set as primary", contactId);
	}
}
