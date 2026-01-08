package com.msg.customs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.msg.customs.adapter.in.web.NotificationAPI.NotificationStatsDto;
import com.msg.customs.adapter.in.web.model.CustomsDataDto;
import com.msg.customs.adapter.in.web.model.CustomsNotificationDto;
import com.msg.customs.adapter.in.web.model.SupplierDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * UI Controller für Notification-Seiten (Epic 4).
 */
@Slf4j
@Controller
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final RestTemplate restTemplate;

    @Value("${customs.backend.url:http://localhost:8080}")
    private String backendUrl;

    // ==================== Ausgehende Notifications ====================

    @GetMapping("/outgoing")
    public String showOutgoingNotifications(Model model) {
        log.debug("Loading outgoing notifications page");
        try {
            ResponseEntity<List<CustomsNotificationDto>> response = restTemplate.exchange(
                    backendUrl + "/notifications/outgoing",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<CustomsNotificationDto>>() {}
            );
            model.addAttribute("notificationList", response.getBody());

            // Statistiken laden
            ResponseEntity<NotificationStatsDto> statsResponse = restTemplate.getForEntity(
                    backendUrl + "/notifications/stats",
                    NotificationStatsDto.class
            );
            model.addAttribute("stats", statsResponse.getBody());

        } catch (Exception e) {
            log.error("Error loading outgoing notifications", e);
            model.addAttribute("error", "Fehler beim Laden der Benachrichtigungen: " + e.getMessage());
        }
        return "notifications-outgoing";
    }

    // ==================== Eingehende Notifications ====================

    @GetMapping("/incoming")
    public String showIncomingNotifications(Model model) {
        log.debug("Loading incoming notifications page");
        try {
            ResponseEntity<List<CustomsNotificationDto>> response = restTemplate.exchange(
                    backendUrl + "/notifications/incoming",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<CustomsNotificationDto>>() {}
            );
            model.addAttribute("notificationList", response.getBody());

            // Statistiken laden
            ResponseEntity<NotificationStatsDto> statsResponse = restTemplate.getForEntity(
                    backendUrl + "/notifications/stats",
                    NotificationStatsDto.class
            );
            model.addAttribute("stats", statsResponse.getBody());

        } catch (Exception e) {
            log.error("Error loading incoming notifications", e);
            model.addAttribute("error", "Fehler beim Laden der Benachrichtigungen: " + e.getMessage());
        }
        return "notifications-incoming";
    }

    // ==================== Notification erstellen ====================

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        log.debug("Loading create notification form");
        loadSupplierList(model);
        loadLleList(model);
        return "notificationCreate";
    }

    // ==================== Notification Detail ====================

    @GetMapping("/view/{id}")
    public String showNotificationDetail(@PathVariable Long id, Model model) {
        log.debug("Loading notification detail for {}", id);
        try {
            ResponseEntity<CustomsNotificationDto> response = restTemplate.getForEntity(
                    backendUrl + "/notifications/" + id,
                    CustomsNotificationDto.class
            );
            model.addAttribute("notification", response.getBody());
        } catch (Exception e) {
            log.error("Error loading notification {}", id, e);
            model.addAttribute("error", "Fehler beim Laden der Benachrichtigung: " + e.getMessage());
        }
        return "notificationDetail";
    }

    // ==================== Hilfsmethoden ====================

    private void loadSupplierList(Model model) {
        try {
            ResponseEntity<List<SupplierDto>> response = restTemplate.exchange(
                    backendUrl + "/suppliers",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<SupplierDto>>() {}
            );
            model.addAttribute("supplierList", response.getBody());
        } catch (Exception e) {
            log.warn("Error loading supplier list", e);
            model.addAttribute("supplierList", List.of());
        }
    }

    private void loadLleList(Model model) {
        try {
            ResponseEntity<List<CustomsDataDto>> response = restTemplate.exchange(
                    backendUrl + "/customs",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<CustomsDataDto>>() {}
            );
            model.addAttribute("lleList", response.getBody());
        } catch (Exception e) {
            log.warn("Error loading LLE list", e);
            model.addAttribute("lleList", List.of());
        }
    }
}
