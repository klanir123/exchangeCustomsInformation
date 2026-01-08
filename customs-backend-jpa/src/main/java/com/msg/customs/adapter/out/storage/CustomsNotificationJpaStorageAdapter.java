package com.msg.customs.adapter.out.storage;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.msg.customs.adapter.out.storage.mapper.CustomsNotificationEntityMapper;
import com.msg.customs.adapter.out.storage.model.CustomsNotificationEntity;
import com.msg.customs.adapter.out.storage.model.NotificationItemEntity;
import com.msg.customs.adapter.out.storage.repository.CustomsNotificationRepository;
import com.msg.customs.adapter.out.storage.repository.NotificationItemRepository;
import com.msg.customs.application.domain.model.CustomsNotification;
import com.msg.customs.application.domain.model.NotificationItem;
import com.msg.customs.application.port.out.CustomsNotificationStorageAdapter;
import com.msg.customs.model.NotificationDirection;
import com.msg.customs.model.NotificationStatus;

import lombok.RequiredArgsConstructor;

/**
 * JPA-Implementierung des CustomsNotificationStorageAdapter.
 */
@Component
@RequiredArgsConstructor
public class CustomsNotificationJpaStorageAdapter implements CustomsNotificationStorageAdapter {

    private final CustomsNotificationRepository notificationRepository;
    private final NotificationItemRepository itemRepository;
    private final CustomsNotificationEntityMapper mapper;

    @Override
    public CustomsNotification save(CustomsNotification notification) {
        CustomsNotificationEntity entity = mapper.toEntity(notification);
        CustomsNotificationEntity saved = notificationRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<CustomsNotification> findById(Long id) {
        return notificationRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<CustomsNotification> findAll() {
        return mapper.toDomainList(notificationRepository.findAll());
    }

    @Override
    public List<CustomsNotification> findByDirection(NotificationDirection direction) {
        return mapper.toDomainList(notificationRepository.findByDirection(direction));
    }

    @Override
    public List<CustomsNotification> findByStatus(NotificationStatus status) {
        return mapper.toDomainList(notificationRepository.findByStatus(status));
    }

    @Override
    public List<CustomsNotification> findByDirectionAndStatus(
            NotificationDirection direction, NotificationStatus status) {
        return mapper.toDomainList(
                notificationRepository.findByDirectionAndStatus(direction, status));
    }

    @Override
    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public Optional<NotificationItem> findItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .map(mapper::itemToDomain);
    }

    @Override
    public NotificationItem saveItem(NotificationItem item) {
        NotificationItemEntity entity = mapper.itemToEntity(item);
        NotificationItemEntity saved = itemRepository.save(entity);
        return mapper.itemToDomain(saved);
    }

    @Override
    public long countUnprocessedIncoming() {
        return notificationRepository.countUnprocessedIncoming();
    }

    @Override
    public long countByStatus(NotificationStatus status) {
        return notificationRepository.countByStatus(status);
    }

    @Override
    public long countByDirection(NotificationDirection direction) {
        return notificationRepository.countByDirection(direction);
    }
}
