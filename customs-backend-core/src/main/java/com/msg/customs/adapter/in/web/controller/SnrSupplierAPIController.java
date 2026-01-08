package com.msg.customs.adapter.in.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.msg.customs.adapter.in.web.SnrSupplierAPI;
import com.msg.customs.adapter.in.web.mapper.SnrSupplierValueMapper;
import com.msg.customs.adapter.in.web.model.SnrSupplierLinkDto;
import com.msg.customs.application.domain.model.SnrSupplierLink;
import com.msg.customs.application.port.in.ManageSnrSupplierUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller für die SNR-Lieferanten-Verknüpfungen.
 * Implementiert FR-2.3 aus dem Lastenheft.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class SnrSupplierAPIController implements SnrSupplierAPI {

	private final ManageSnrSupplierUseCase manageSnrSupplierUseCase;

	// ==================== SNR -> Lieferanten ====================

	@Override
	public ResponseEntity<List<SnrSupplierLinkDto>> getSuppliersForSnr(Long masterpartId) {
		log.debug("GET /customs/parts/{}/suppliers - Loading suppliers for SNR", masterpartId);
		List<SnrSupplierLink> suppliers = manageSnrSupplierUseCase.getSuppliersForSnr(masterpartId);
		return ResponseEntity.ok(SnrSupplierValueMapper.toDtoList(suppliers));
	}

	@Override
	public ResponseEntity<Void> addSupplierToSnr(Long masterpartId, SnrSupplierLinkDto linkDto) {
		log.info("POST /customs/parts/{}/suppliers - Adding supplier {} to SNR",
			masterpartId, linkDto.getSupplierBpnl());

		manageSnrSupplierUseCase.addSupplierToSnr(
			masterpartId,
			linkDto.getSupplierBpnl(),
			linkDto.isPrimary(),
			linkDto.getNote()
		);

		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> removeSupplierFromSnr(Long masterpartId, String supplierBpnl) {
		log.info("DELETE /customs/parts/{}/suppliers/{} - Removing supplier from SNR",
			masterpartId, supplierBpnl);

		manageSnrSupplierUseCase.removeSupplierFromSnr(masterpartId, supplierBpnl);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> setPrimarySupplier(Long masterpartId, String supplierBpnl) {
		log.info("PATCH /customs/parts/{}/suppliers/{}/primary - Setting supplier as primary",
			masterpartId, supplierBpnl);

		manageSnrSupplierUseCase.setPrimarySupplier(masterpartId, supplierBpnl);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> updateSnrSupplierLink(Long masterpartId, String supplierBpnl,
			SnrSupplierLinkDto linkDto) {
		log.info("PUT /customs/parts/{}/suppliers/{} - Updating link note",
			masterpartId, supplierBpnl);

		manageSnrSupplierUseCase.updateLinkNote(masterpartId, supplierBpnl, linkDto.getNote());
		return ResponseEntity.ok().build();
	}

	// ==================== Lieferant -> SNRs ====================

	@Override
	public ResponseEntity<List<SnrSupplierLinkDto>> getSnrsForSupplier(String supplierBpnl) {
		log.debug("GET /suppliers/{}/snrs - Loading SNRs for supplier", supplierBpnl);
		List<SnrSupplierLink> snrs = manageSnrSupplierUseCase.getSnrsForSupplier(supplierBpnl);
		return ResponseEntity.ok(SnrSupplierValueMapper.toDtoList(snrs));
	}

	@Override
	public ResponseEntity<Boolean> linkExists(Long masterpartId, String supplierBpnl) {
		log.debug("GET /customs/parts/{}/suppliers/{}/exists - Checking link exists",
			masterpartId, supplierBpnl);

		boolean exists = manageSnrSupplierUseCase.linkExists(masterpartId, supplierBpnl);
		return ResponseEntity.ok(exists);
	}
}
