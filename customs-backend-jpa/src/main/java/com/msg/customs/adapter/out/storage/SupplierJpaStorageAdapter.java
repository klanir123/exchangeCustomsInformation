package com.msg.customs.adapter.out.storage;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.msg.customs.adapter.out.storage.mapper.SupplierEntityMapper;
import com.msg.customs.adapter.out.storage.model.SupplierContactEntity;
import com.msg.customs.adapter.out.storage.model.SupplierEntity;
import com.msg.customs.adapter.out.storage.repository.SupplierContactRepository;
import com.msg.customs.adapter.out.storage.repository.SupplierRepository;
import com.msg.customs.application.domain.model.Supplier;
import com.msg.customs.application.domain.model.SupplierContact;
import com.msg.customs.application.port.out.SupplierStorageAdapter;
import com.msg.customs.model.SupplierStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JPA Implementation des SupplierStorageAdapter.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class SupplierJpaStorageAdapter implements SupplierStorageAdapter {

	private final SupplierRepository supplierRepository;
	private final SupplierContactRepository contactRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Supplier> findAll() {
		log.debug("Finding all suppliers");
		return SupplierEntityMapper.toDomainList(supplierRepository.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Supplier> findByStatus(SupplierStatus status) {
		log.debug("Finding suppliers by status: {}", status);
		return SupplierEntityMapper.toDomainList(supplierRepository.findByStatus(status));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Supplier> findByBpnl(String bpnl) {
		log.debug("Finding supplier by BPNL: {}", bpnl);
		return supplierRepository.findById(bpnl)
			.map(SupplierEntityMapper::toDomain);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Supplier> searchByBpnlOrName(String query) {
		log.debug("Searching suppliers with query: {}", query);
		return SupplierEntityMapper.toDomainList(supplierRepository.searchByBpnlOrName(query));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByBpnl(String bpnl) {
		return supplierRepository.existsByBpnl(bpnl);
	}

	@Override
	public Supplier save(Supplier supplier) {
		log.debug("Saving supplier: {}", supplier.getBpnl());

		SupplierEntity entity;

		// Prüfen ob Update oder Create
		Optional<SupplierEntity> existing = supplierRepository.findById(supplier.getBpnl());
		if (existing.isPresent()) {
			// Update: Existierende Entity aktualisieren
			entity = existing.get();
			entity.setName(supplier.getName());
			entity.setStreet(supplier.getStreet());
			entity.setPostalCode(supplier.getPostalCode());
			entity.setCity(supplier.getCity());
			entity.setCountry(supplier.getCountry());
			entity.setStatus(supplier.getStatus());
		} else {
			// Create: Neue Entity
			entity = SupplierEntityMapper.toEntity(supplier);
		}

		SupplierEntity saved = supplierRepository.save(entity);
		log.info("Supplier saved: {}", saved.getBpnl());

		return SupplierEntityMapper.toDomain(saved);
	}

	@Override
	public void deleteByBpnl(String bpnl) {
		log.debug("Deleting supplier: {}", bpnl);
		supplierRepository.deleteById(bpnl);
		log.info("Supplier deleted: {}", bpnl);
	}

	@Override
	public SupplierContact saveContact(String bpnl, SupplierContact contact) {
		log.debug("Saving contact for supplier: {}", bpnl);

		SupplierEntity supplier = supplierRepository.findById(bpnl)
			.orElseThrow(() -> new IllegalArgumentException("Lieferant nicht gefunden: " + bpnl));

		SupplierContactEntity contactEntity;

		if (contact.getId() != null) {
			// Update existierender Kontakt
			contactEntity = contactRepository.findById(contact.getId())
				.orElseThrow(() -> new IllegalArgumentException("Kontakt nicht gefunden: " + contact.getId()));
		} else {
			// Neuer Kontakt
			contactEntity = new SupplierContactEntity();
			contactEntity.setSupplier(supplier);
		}

		contactEntity.setContactPerson(contact.getContactPerson());
		contactEntity.setPhoneNumber(contact.getPhoneNumber());
		contactEntity.setEmail(contact.getEmail());
		contactEntity.setJobTitle(contact.getJobTitle());
		contactEntity.setPrimary(contact.isPrimary());
		contactEntity.setPreferredForNotifications(contact.isPreferredForNotifications());

		SupplierContactEntity saved = contactRepository.save(contactEntity);
		log.info("Contact saved: {}", saved.getId());

		return SupplierEntityMapper.toContactDomain(saved);
	}

	@Override
	public void deleteContact(String bpnl, Long contactId) {
		log.debug("Deleting contact {} from supplier {}", contactId, bpnl);

		// Verify contact exists and belongs to supplier
		SupplierContactEntity contact = contactRepository.findById(contactId)
			.orElseThrow(() -> new IllegalArgumentException("Kontakt nicht gefunden: " + contactId));

		if (!contact.getSupplier().getBpnl().equals(bpnl)) {
			throw new IllegalArgumentException("Kontakt gehört nicht zu Lieferant: " + bpnl);
		}

		// Use @Modifying query for direct DB deletion
		int deleted = contactRepository.deleteContactById(contactId);
		log.info("Contact deleted: {} (rows affected: {})", contactId, deleted);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<SupplierContact> findContactById(Long contactId) {
		return contactRepository.findById(contactId)
			.map(SupplierEntityMapper::toContactDomain);
	}
}
