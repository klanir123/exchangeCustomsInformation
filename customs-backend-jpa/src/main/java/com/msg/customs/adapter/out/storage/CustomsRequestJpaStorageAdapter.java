package com.msg.customs.adapter.out.storage;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.msg.customs.adapter.out.storage.mapper.CustomsRequestEntityMapper;
import com.msg.customs.adapter.out.storage.model.CustomsRequestEntity;
import com.msg.customs.adapter.out.storage.model.SupplierEntity;
import com.msg.customs.adapter.out.storage.repository.CustomsRequestRepository;
import com.msg.customs.adapter.out.storage.repository.SupplierRepository;
import com.msg.customs.application.domain.model.CustomsRequest;
import com.msg.customs.application.port.out.CustomsRequestStorageAdapter;
import com.msg.customs.model.NotificationDirection;
import com.msg.customs.model.RequestStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JPA-Implementierung des CustomsRequestStorageAdapter.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomsRequestJpaStorageAdapter implements CustomsRequestStorageAdapter {

    private final CustomsRequestRepository requestRepository;
    private final SupplierRepository supplierRepository;

    @Override
    @Transactional
    public CustomsRequest save(CustomsRequest request) {
        log.debug("Saving CustomsRequest: {}", request.getId());

        SupplierEntity supplier = null;
        if (request.getSupplierBpnl() != null) {
            supplier = supplierRepository.findById(request.getSupplierBpnl())
                    .orElse(null);
        }

        CustomsRequestEntity entity;
        if (request.getId() != null) {
            // Update existing
            entity = requestRepository.findById(request.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Request not found: " + request.getId()));
            CustomsRequestEntityMapper.updateEntity(entity, request);
            if (supplier != null) {
                entity.setSupplier(supplier);
            }
        } else {
            // Create new
            entity = CustomsRequestEntityMapper.toEntity(request, supplier);
        }

        CustomsRequestEntity saved = requestRepository.save(entity);
        log.info("CustomsRequest saved with ID: {}", saved.getId());

        return CustomsRequestEntityMapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomsRequest> findById(Long id) {
        return requestRepository.findById(id)
                .map(CustomsRequestEntityMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomsRequest> findByMessageId(String messageId) {
        return requestRepository.findByMessageId(messageId)
                .map(CustomsRequestEntityMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomsRequest> findAll() {
        return CustomsRequestEntityMapper.toDomainList(
                requestRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomsRequest> findByDirection(NotificationDirection direction) {
        return CustomsRequestEntityMapper.toDomainList(
                requestRepository.findByDirectionOrderByCreatedAtDesc(direction));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomsRequest> findByStatus(RequestStatus status) {
        return CustomsRequestEntityMapper.toDomainList(
                requestRepository.findByStatusOrderByCreatedAtDesc(status));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomsRequest> findByStatusAndDirection(RequestStatus status, NotificationDirection direction) {
        return CustomsRequestEntityMapper.toDomainList(
                requestRepository.findByStatusAndDirectionOrderByCreatedAtDesc(status, direction));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomsRequest> findBySupplierBpnl(String supplierBpnl) {
        return CustomsRequestEntityMapper.toDomainList(
                requestRepository.findBySupplierBpnlOrderByCreatedAtDesc(supplierBpnl));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomsRequest> findOverdueRequests() {
        return CustomsRequestEntityMapper.toDomainList(
                requestRepository.findOverdueRequests());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.debug("Deleting CustomsRequest: {}", id);
        requestRepository.deleteById(id);
        log.info("CustomsRequest deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return requestRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countOpenOutgoingRequests() {
        return requestRepository.countOpenOutgoingRequests();
    }

    @Override
    @Transactional(readOnly = true)
    public long countOpenIncomingRequests() {
        return requestRepository.countOpenIncomingRequests();
    }

    @Override
    @Transactional(readOnly = true)
    public long countOverdueRequests() {
        return requestRepository.countOverdueRequests();
    }
}
