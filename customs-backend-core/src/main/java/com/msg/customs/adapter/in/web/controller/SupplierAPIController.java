package com.msg.customs.adapter.in.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.msg.customs.adapter.in.web.SupplierAPI;
import com.msg.customs.adapter.in.web.mapper.SupplierValueMapper;
import com.msg.customs.adapter.in.web.model.SupplierContactDto;
import com.msg.customs.adapter.in.web.model.SupplierDto;
import com.msg.customs.application.domain.model.Supplier;
import com.msg.customs.application.domain.model.SupplierContact;
import com.msg.customs.application.port.in.LoadSupplierListUseCase;
import com.msg.customs.application.port.in.SaveSupplierUseCase;
import com.msg.customs.model.SupplierStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller für die Lieferantenverwaltung.
 * Implementiert Epic 1 aus dem Lastenheft.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class SupplierAPIController implements SupplierAPI {

	private final LoadSupplierListUseCase loadSupplierUseCase;
	private final SaveSupplierUseCase saveSupplierUseCase;

	// ==================== Lieferanten CRUD ====================

	@Override
	public ResponseEntity<List<SupplierDto>> getAllSuppliers() {
		log.debug("GET /suppliers - Loading all suppliers");
		List<Supplier> suppliers = loadSupplierUseCase.loadAllSuppliers();
		return ResponseEntity.ok(SupplierValueMapper.toDtoList(suppliers));
	}

	@Override
	public ResponseEntity<List<SupplierDto>> getActiveSuppliers() {
		log.debug("GET /suppliers/active - Loading active suppliers");
		List<Supplier> suppliers = loadSupplierUseCase.loadActiveSuppliers();
		return ResponseEntity.ok(SupplierValueMapper.toDtoList(suppliers));
	}

	@Override
	public ResponseEntity<SupplierDto> getSupplierByBpnl(String bpnl) {
		log.debug("GET /suppliers/{} - Loading supplier", bpnl);
		Supplier supplier = loadSupplierUseCase.loadSupplierByBpnl(bpnl);
		return ResponseEntity.ok(SupplierValueMapper.toDto(supplier));
	}

	@Override
	public ResponseEntity<Void> createSupplier(SupplierDto supplierDto) {
		log.info("POST /suppliers - Creating supplier: {}", supplierDto.getBpnl());
		Supplier supplier = SupplierValueMapper.toDomain(supplierDto);
		saveSupplierUseCase.createSupplier(supplier);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> updateSupplier(String bpnl, SupplierDto supplierDto) {
		log.info("PUT /suppliers/{} - Updating supplier", bpnl);
		Supplier supplier = SupplierValueMapper.toDomain(supplierDto);
		saveSupplierUseCase.updateSupplier(bpnl, supplier);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> updateSupplierStatus(String bpnl, SupplierStatus status) {
		log.info("PATCH /suppliers/{}/status - Updating status to {}", bpnl, status);
		saveSupplierUseCase.updateSupplierStatus(bpnl, status);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> deleteSupplier(String bpnl) {
		log.info("DELETE /suppliers/{} - Deleting supplier", bpnl);
		saveSupplierUseCase.deleteSupplier(bpnl);
		return ResponseEntity.ok().build();
	}

	// ==================== Suche ====================

	@Override
	public ResponseEntity<List<SupplierDto>> searchSuppliers(String query) {
		log.debug("GET /suppliers/search?query={} - Searching suppliers", query);
		List<Supplier> suppliers = loadSupplierUseCase.searchSuppliers(query);
		return ResponseEntity.ok(SupplierValueMapper.toDtoList(suppliers));
	}

	@Override
	public ResponseEntity<Boolean> existsByBpnl(String bpnl) {
		log.debug("GET /suppliers/exists/{} - Checking BPNL", bpnl);
		boolean exists = loadSupplierUseCase.existsByBpnl(bpnl);
		return ResponseEntity.ok(exists);
	}

	// ==================== Kontakte ====================

	@Override
	public ResponseEntity<List<SupplierContactDto>> getSupplierContacts(String bpnl) {
		log.debug("GET /suppliers/{}/contacts - Loading contacts", bpnl);
		Supplier supplier = loadSupplierUseCase.loadSupplierByBpnl(bpnl);
		List<SupplierContactDto> contacts = supplier.getContacts().stream()
			.map(SupplierValueMapper::toContactDto)
			.toList();
		return ResponseEntity.ok(contacts);
	}

	@Override
	public ResponseEntity<Void> addSupplierContact(String bpnl, SupplierContactDto contactDto) {
		log.info("POST /suppliers/{}/contacts - Adding contact", bpnl);
		SupplierContact contact = SupplierValueMapper.toContactDomain(contactDto);
		saveSupplierUseCase.addContact(bpnl, contact);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> updateSupplierContact(String bpnl, Long contactId, SupplierContactDto contactDto) {
		log.info("PUT /suppliers/{}/contacts/{} - Updating contact", bpnl, contactId);
		SupplierContact contact = SupplierValueMapper.toContactDomain(contactDto);
		saveSupplierUseCase.updateContact(bpnl, contactId, contact);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> deleteSupplierContact(String bpnl, Long contactId) {
		log.info("DELETE /suppliers/{}/contacts/{} - Deleting contact", bpnl, contactId);
		saveSupplierUseCase.deleteContact(bpnl, contactId);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> setContactAsPrimary(String bpnl, Long contactId) {
		log.info("PATCH /suppliers/{}/contacts/{}/primary - Setting as primary", bpnl, contactId);
		saveSupplierUseCase.setContactAsPrimary(bpnl, contactId);
		return ResponseEntity.ok().build();
	}
}
