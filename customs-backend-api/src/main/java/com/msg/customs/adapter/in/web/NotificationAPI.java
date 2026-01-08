package com.msg.customs.adapter.in.web;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msg.customs.adapter.in.web.model.CreateNotificationDto;
import com.msg.customs.adapter.in.web.model.CustomsNotificationDto;
import com.msg.customs.adapter.in.web.model.NotificationItemDto;
import com.msg.customs.model.NotificationStatus;

/**
 * API für CustomsNotification (Epic 4/6).
 * Ermöglicht das Senden und Empfangen von LLE-Daten.
 */
@RequestMapping(value = "/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
public interface NotificationAPI {

    // ==================== Allgemeine Endpunkte ====================

    /**
     * Alle Notifications abrufen.
     */
    @GetMapping
    ResponseEntity<List<CustomsNotificationDto>> getAllNotifications();

    /**
     * Notification nach ID abrufen.
     */
    @GetMapping("/{id}")
    ResponseEntity<CustomsNotificationDto> getNotificationById(@PathVariable Long id);

    /**
     * Notification löschen (nur im DRAFT-Status).
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteNotification(@PathVariable Long id);

    // ==================== Ausgehende Notifications ====================

    /**
     * Neue Notification erstellen.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CustomsNotificationDto> createNotification(@RequestBody CreateNotificationDto dto);

    /**
     * Notification versenden.
     */
    @PostMapping("/{id}/send")
    ResponseEntity<CustomsNotificationDto> sendNotification(@PathVariable Long id);

    /**
     * Alle ausgehenden Notifications abrufen.
     */
    @GetMapping("/outgoing")
    ResponseEntity<List<CustomsNotificationDto>> getOutgoingNotifications();

    /**
     * Ausgehende Notifications nach Status filtern.
     */
    @GetMapping("/outgoing/by-status/{status}")
    ResponseEntity<List<CustomsNotificationDto>> getOutgoingNotificationsByStatus(
            @PathVariable NotificationStatus status);

    // ==================== Eingehende Notifications ====================

    /**
     * Alle eingehenden Notifications abrufen.
     */
    @GetMapping("/incoming")
    ResponseEntity<List<CustomsNotificationDto>> getIncomingNotifications();

    /**
     * Eingehende Notifications nach Status filtern.
     */
    @GetMapping("/incoming/by-status/{status}")
    ResponseEntity<List<CustomsNotificationDto>> getIncomingNotificationsByStatus(
            @PathVariable NotificationStatus status);

    /**
     * Simuliert den Empfang einer Notification (für Tests).
     */
    @PostMapping(value = "/receive", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CustomsNotificationDto> receiveNotification(@RequestBody CustomsNotificationDto dto);

    /**
     * Notification als verarbeitet markieren.
     */
    @PostMapping("/{id}/process")
    ResponseEntity<CustomsNotificationDto> markAsProcessed(@PathVariable Long id);

    // ==================== Item-Verwaltung ====================

    /**
     * Items einer Notification abrufen.
     */
    @GetMapping("/{id}/items")
    ResponseEntity<List<NotificationItemDto>> getNotificationItems(@PathVariable Long id);

    /**
     * Item in eigene LLE übernehmen.
     */
    @PostMapping("/{notificationId}/items/{itemId}/adopt")
    ResponseEntity<Void> adoptItem(
            @PathVariable Long notificationId,
            @PathVariable Long itemId,
            @RequestParam(required = false) Long snrId);

    /**
     * Alle Items einer Notification übernehmen.
     */
    @PostMapping("/{id}/adopt-all")
    ResponseEntity<Void> adoptAllItems(@PathVariable Long id);

    // ==================== Statistiken ====================

    /**
     * Notification-Statistiken für Dashboard.
     */
    @GetMapping("/stats")
    ResponseEntity<NotificationStatsDto> getNotificationStats();

    /**
     * DTO für Notification-Statistiken.
     */
    record NotificationStatsDto(
            long outgoingTotal,
            long outgoingDraft,
            long outgoingSent,
            long incomingTotal,
            long incomingReceived,
            long incomingProcessed,
            long incomingUnprocessed
    ) {}
}
