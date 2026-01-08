package com.msg.customs.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.msg.customs.application.domain.model.SnrSupplierLink;
import com.msg.customs.application.port.in.ManageSnrSupplierUseCase;
import com.msg.customs.application.port.out.SnrSupplierStorageAdapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service für die Verwaltung der SNR-Lieferanten-Verknüpfungen.
 * Implementiert FR-2.3 aus dem Lastenheft.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SnrSupplierService implements ManageSnrSupplierUseCase {

	private final SnrSupplierStorageAdapter storageAdapter;

	@Override
	public void addSupplierToSnr(Long masterpartId, String supplierBpnl, boolean isPrimary, String note) {
		log.info("Adding supplier {} to SNR {} (primary={})", supplierBpnl, masterpartId, isPrimary);

		// Prüfen ob Verknüpfung bereits existiert
		if (storageAdapter.exists(masterpartId, supplierBpnl)) {
			throw new IllegalArgumentException(
				"Lieferant " + supplierBpnl + " ist bereits mit SNR " + masterpartId + " verknüpft"
			);
		}

		// Wenn primär, dann andere zurücksetzen
		if (isPrimary) {
			storageAdapter.resetPrimaryForSnr(masterpartId);
		}

		SnrSupplierLink link = new SnrSupplierLink();
		link.setMasterpartId(masterpartId);
		link.setSupplierBpnl(supplierBpnl);
		link.setPrimary(isPrimary);
		link.setNote(note);

		storageAdapter.save(link);
		log.info("Supplier {} added to SNR {}", supplierBpnl, masterpartId);
	}

	@Override
	public void removeSupplierFromSnr(Long masterpartId, String supplierBpnl) {
		log.info("Removing supplier {} from SNR {}", supplierBpnl, masterpartId);

		if (!storageAdapter.exists(masterpartId, supplierBpnl)) {
			throw new IllegalArgumentException(
				"Verknüpfung zwischen Lieferant " + supplierBpnl + " und SNR " + masterpartId + " existiert nicht"
			);
		}

		storageAdapter.delete(masterpartId, supplierBpnl);
		log.info("Supplier {} removed from SNR {}", supplierBpnl, masterpartId);
	}

	@Override
	public void setPrimarySupplier(Long masterpartId, String supplierBpnl) {
		log.info("Setting supplier {} as primary for SNR {}", supplierBpnl, masterpartId);

		SnrSupplierLink link = storageAdapter.findById(masterpartId, supplierBpnl)
			.orElseThrow(() -> new IllegalArgumentException(
				"Verknüpfung zwischen Lieferant " + supplierBpnl + " und SNR " + masterpartId + " existiert nicht"
			));

		// Alle anderen zurücksetzen
		storageAdapter.resetPrimaryForSnr(masterpartId);

		// Aktuellen als primär setzen
		link.setPrimary(true);
		storageAdapter.save(link);

		log.info("Supplier {} is now primary for SNR {}", supplierBpnl, masterpartId);
	}

	@Override
	public void updateLinkNote(Long masterpartId, String supplierBpnl, String note) {
		log.debug("Updating note for link {} - {}", masterpartId, supplierBpnl);

		SnrSupplierLink link = storageAdapter.findById(masterpartId, supplierBpnl)
			.orElseThrow(() -> new IllegalArgumentException(
				"Verknüpfung zwischen Lieferant " + supplierBpnl + " und SNR " + masterpartId + " existiert nicht"
			));

		link.setNote(note);
		storageAdapter.save(link);
	}

	@Override
	public List<SnrSupplierLink> getSuppliersForSnr(Long masterpartId) {
		log.debug("Loading suppliers for SNR {}", masterpartId);
		return storageAdapter.findByMasterpartId(masterpartId);
	}

	@Override
	public List<SnrSupplierLink> getSnrsForSupplier(String supplierBpnl) {
		log.debug("Loading SNRs for supplier {}", supplierBpnl);
		return storageAdapter.findBySupplierBpnl(supplierBpnl);
	}

	@Override
	public boolean linkExists(Long masterpartId, String supplierBpnl) {
		return storageAdapter.exists(masterpartId, supplierBpnl);
	}
}
