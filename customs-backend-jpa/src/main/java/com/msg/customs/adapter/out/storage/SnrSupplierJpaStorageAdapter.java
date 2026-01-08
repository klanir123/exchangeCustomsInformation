package com.msg.customs.adapter.out.storage;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.msg.customs.adapter.out.storage.mapper.SnrSupplierEntityMapper;
import com.msg.customs.adapter.out.storage.model.MasterPartEntity;
import com.msg.customs.adapter.out.storage.model.SnrSupplierEntity;
import com.msg.customs.adapter.out.storage.model.SnrSupplierId;
import com.msg.customs.adapter.out.storage.model.SupplierEntity;
import com.msg.customs.adapter.out.storage.repository.MasterPartRepository;
import com.msg.customs.adapter.out.storage.repository.SnrSupplierRepository;
import com.msg.customs.adapter.out.storage.repository.SupplierRepository;
import com.msg.customs.application.domain.model.SnrSupplierLink;
import com.msg.customs.application.port.out.SnrSupplierStorageAdapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JPA Implementation des SnrSupplierStorageAdapter.
 * Verwaltet die n:m Beziehung zwischen SNR und Lieferant.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class SnrSupplierJpaStorageAdapter implements SnrSupplierStorageAdapter {

	private final SnrSupplierRepository snrSupplierRepository;
	private final MasterPartRepository masterPartRepository;
	private final SupplierRepository supplierRepository;

	@Override
	public SnrSupplierLink save(SnrSupplierLink link) {
		log.debug("Saving SNR-Supplier link: {} - {}", link.getMasterpartId(), link.getSupplierBpnl());

		SnrSupplierId id = new SnrSupplierId(link.getMasterpartId(), link.getSupplierBpnl());

		SnrSupplierEntity entity = snrSupplierRepository.findById(id)
			.orElseGet(() -> {
				MasterPartEntity masterpart = masterPartRepository.findById(link.getMasterpartId())
					.orElseThrow(() -> new IllegalArgumentException(
						"SNR nicht gefunden: " + link.getMasterpartId()));

				SupplierEntity supplier = supplierRepository.findById(link.getSupplierBpnl())
					.orElseThrow(() -> new IllegalArgumentException(
						"Lieferant nicht gefunden: " + link.getSupplierBpnl()));

				return new SnrSupplierEntity(masterpart, supplier);
			});

		entity.setPrimary(link.isPrimary());
		entity.setNote(link.getNote());

		SnrSupplierEntity saved = snrSupplierRepository.save(entity);
		log.info("SNR-Supplier link saved: {} - {}", saved.getId().getMasterpartId(), saved.getId().getSupplierBpnl());

		return SnrSupplierEntityMapper.toDomain(saved);
	}

	@Override
	public void delete(Long masterpartId, String supplierBpnl) {
		log.debug("Deleting SNR-Supplier link: {} - {}", masterpartId, supplierBpnl);

		SnrSupplierId id = new SnrSupplierId(masterpartId, supplierBpnl);
		snrSupplierRepository.deleteById(id);

		log.info("SNR-Supplier link deleted: {} - {}", masterpartId, supplierBpnl);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SnrSupplierLink> findByMasterpartId(Long masterpartId) {
		log.debug("Finding suppliers for SNR: {}", masterpartId);
		return SnrSupplierEntityMapper.toDomainList(
			snrSupplierRepository.findByMasterpartId(masterpartId));
	}

	@Override
	@Transactional(readOnly = true)
	public List<SnrSupplierLink> findBySupplierBpnl(String bpnl) {
		log.debug("Finding SNRs for supplier: {}", bpnl);
		return SnrSupplierEntityMapper.toDomainList(
			snrSupplierRepository.findBySupplierBpnl(bpnl));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<SnrSupplierLink> findById(Long masterpartId, String supplierBpnl) {
		log.debug("Finding SNR-Supplier link: {} - {}", masterpartId, supplierBpnl);

		SnrSupplierId id = new SnrSupplierId(masterpartId, supplierBpnl);
		return snrSupplierRepository.findById(id)
			.map(SnrSupplierEntityMapper::toDomain);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean exists(Long masterpartId, String supplierBpnl) {
		return snrSupplierRepository.existsByMasterpartIdAndSupplierBpnl(masterpartId, supplierBpnl);
	}

	@Override
	public void resetPrimaryForSnr(Long masterpartId) {
		log.debug("Resetting primary status for SNR: {}", masterpartId);

		List<SnrSupplierEntity> links = snrSupplierRepository.findByMasterpartId(masterpartId);
		for (SnrSupplierEntity link : links) {
			if (link.isPrimary()) {
				link.setPrimary(false);
				snrSupplierRepository.save(link);
			}
		}

		log.debug("Primary status reset for SNR: {}", masterpartId);
	}
}
