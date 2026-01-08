package com.msg.customs.adapter.in.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.msg.customs.adapter.in.web.NotificationAPI;
import com.msg.customs.adapter.in.web.mapper.CustomsNotificationValueMapper;
import com.msg.customs.adapter.in.web.model.CreateNotificationDto;
import com.msg.customs.adapter.in.web.model.CustomsNotificationDto;
import com.msg.customs.adapter.in.web.model.NotificationItemDto;
import com.msg.customs.application.domain.model.CustomsNotification;
import com.msg.customs.application.domain.service.CustomsNotificationService;
import com.msg.customs.model.NotificationStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller für CustomsNotification (Epic 4).
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationAPIController implements NotificationAPI {

    private final CustomsNotificationService notificationService;
    private final CustomsNotificationValueMapper mapper;

    // ==================== Allgemeine Endpunkte ====================

    @Override
    public ResponseEntity<List<CustomsNotificationDto>> getAllNotifications() {
        log.debug("GET /notifications - Getting all notifications");
        List<CustomsNotification> notifications = notificationService.findAll();
        return ResponseEntity.ok(mapper.toDtoList(notifications));
    }

    @Override
    public ResponseEntity<CustomsNotificationDto> getNotificationById(Long id) {
        log.debug("GET /notifications/{} - Getting notification by ID", id);
        CustomsNotification notification = notificationService.findById(id);
        return ResponseEntity.ok(mapper.toDto(notification));
    }

    @Override
    public ResponseEntity<Void> deleteNotification(Long id) {
        log.debug("DELETE /notifications/{} - Deleting notification", id);
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== Ausgehende Notifications ====================

    @Override
    public ResponseEntity<CustomsNotificationDto> createNotification(CreateNotificationDto dto) {
        log.debug("POST /notifications - Creating notification for receiver {}", dto.getReceiverBpnl());
        CustomsNotification notification = notificationService.createNotification(
                dto.getReceiverBpnl(),
                dto.getLleIds(),
                dto.getRequestId(),
                dto.getRemarks()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(notification));
    }

    @Override
    public ResponseEntity<CustomsNotificationDto> sendNotification(Long id) {
        log.debug("POST /notifications/{}/send - Sending notification", id);
        CustomsNotification notification = notificationService.sendNotification(id);
        return ResponseEntity.ok(mapper.toDto(notification));
    }

    @Override
    public ResponseEntity<List<CustomsNotificationDto>> getOutgoingNotifications() {
        log.debug("GET /notifications/outgoing - Getting outgoing notifications");
        List<CustomsNotification> notifications = notificationService.findOutgoingNotifications();
        return ResponseEntity.ok(mapper.toDtoList(notifications));
    }

    @Override
    public ResponseEntity<List<CustomsNotificationDto>> getOutgoingNotificationsByStatus(NotificationStatus status) {
        log.debug("GET /notifications/outgoing/by-status/{} - Getting outgoing by status", status);
        List<CustomsNotification> notifications = notificationService.findOutgoingNotificationsByStatus(status);
        return ResponseEntity.ok(mapper.toDtoList(notifications));
    }

    // ==================== Eingehende Notifications ====================

    @Override
    public ResponseEntity<List<CustomsNotificationDto>> getIncomingNotifications() {
        log.debug("GET /notifications/incoming - Getting incoming notifications");
        List<CustomsNotification> notifications = notificationService.findIncomingNotifications();
        return ResponseEntity.ok(mapper.toDtoList(notifications));
    }

    @Override
    public ResponseEntity<List<CustomsNotificationDto>> getIncomingNotificationsByStatus(NotificationStatus status) {
        log.debug("GET /notifications/incoming/by-status/{} - Getting incoming by status", status);
        List<CustomsNotification> notifications = notificationService.findIncomingNotificationsByStatus(status);
        return ResponseEntity.ok(mapper.toDtoList(notifications));
    }

    @Override
    public ResponseEntity<CustomsNotificationDto> receiveNotification(CustomsNotificationDto dto) {
        log.debug("POST /notifications/receive - Receiving notification");
        CustomsNotification domain = mapper.toDomain(dto);
        CustomsNotification received = notificationService.receiveNotification(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(received));
    }

    @Override
    public ResponseEntity<CustomsNotificationDto> markAsProcessed(Long id) {
        log.debug("POST /notifications/{}/process - Marking as processed", id);
        CustomsNotification notification = notificationService.markAsProcessed(id);
        return ResponseEntity.ok(mapper.toDto(notification));
    }

    // ==================== Item-Verwaltung ====================

    @Override
    public ResponseEntity<List<NotificationItemDto>> getNotificationItems(Long id) {
        log.debug("GET /notifications/{}/items - Getting items", id);
        CustomsNotification notification = notificationService.findById(id);
        return ResponseEntity.ok(mapper.itemsToDtoList(notification.getListOfItems()));
    }

    @Override
    public ResponseEntity<Void> adoptItem(Long notificationId, Long itemId, Long snrId) {
        log.debug("POST /notifications/{}/items/{}/adopt - Adopting item", notificationId, itemId);
        notificationService.adoptItem(notificationId, itemId, snrId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> adoptAllItems(Long id) {
        log.debug("POST /notifications/{}/adopt-all - Adopting all items", id);
        notificationService.adoptAllItems(id);
        return ResponseEntity.ok().build();
    }

    // ==================== Statistiken ====================

    @Override
    public ResponseEntity<NotificationStatsDto> getNotificationStats() {
        log.debug("GET /notifications/stats - Getting statistics");
        CustomsNotificationService.NotificationStats stats = notificationService.getStats();
        return ResponseEntity.ok(new NotificationStatsDto(
                stats.outgoingTotal(),
                stats.outgoingDraft(),
                stats.outgoingSent(),
                stats.incomingTotal(),
                stats.incomingReceived(),
                stats.incomingProcessed(),
                stats.incomingUnprocessed()
        ));
    }
}
