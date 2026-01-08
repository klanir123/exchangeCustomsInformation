package com.msg.customs.adapter.in.web;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msg.customs.adapter.in.web.model.CreateRequestDto;
import com.msg.customs.adapter.in.web.model.CustomsRequestDto;
import com.msg.customs.adapter.in.web.model.ReceiveRequestDto;
import com.msg.customs.model.RequestStatus;

/**
 * API für RequestCustomsInformation (Epic 5).
 * Ermöglicht das Anfragen von Zollinformationen/LLE-Daten bei Lieferanten.
 *
 * User Stories:
 * - 5.3: API-Endpunkte für Request-Verwaltung
 */
@RequestMapping(value = "/requests", produces = MediaType.APPLICATION_JSON_VALUE)
public interface RequestAPI {

    // ==================== Allgemeine Endpunkte ====================

    /**
     * Alle Anfragen abrufen.
     * @return Liste aller Anfragen
     */
    @GetMapping
    ResponseEntity<List<CustomsRequestDto>> getAllRequests();

    /**
     * Anfrage nach ID abrufen.
     * @param id Anfrage-ID
     * @return Anfrage
     */
    @GetMapping(value = "/{id}")
    ResponseEntity<CustomsRequestDto> getRequestById(@PathVariable Long id);

    /**
     * Anfrage löschen (nur im DRAFT-Status).
     * @param id Anfrage-ID
     * @return HTTP Status
     */
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteRequest(@PathVariable Long id);

    // ==================== Ausgehende Anfragen (als Kunde) ====================

    /**
     * Neue Anfrage erstellen (DRAFT).
     * @param request Anfragedaten
     * @return Erstellte Anfrage
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CustomsRequestDto> createRequest(@RequestBody CreateRequestDto request);

    /**
     * Anfrage aktualisieren (nur im DRAFT-Status).
     * @param id Anfrage-ID
     * @param request Aktualisierte Anfragedaten
     * @return Aktualisierte Anfrage
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CustomsRequestDto> updateRequest(@PathVariable Long id, @RequestBody CreateRequestDto request);

    /**
     * Anfrage versenden.
     * Ändert Status von DRAFT zu SENT und initiiert EDC-Transfer.
     * @param id Anfrage-ID
     * @return Versendete Anfrage
     */
    @PostMapping(value = "/{id}/send")
    ResponseEntity<CustomsRequestDto> sendRequest(@PathVariable Long id);

    /**
     * Alle ausgehenden Anfragen abrufen.
     * @return Liste der ausgehenden Anfragen
     */
    @GetMapping(value = "/outgoing")
    ResponseEntity<List<CustomsRequestDto>> getOutgoingRequests();

    /**
     * Ausgehende Anfragen nach Status filtern.
     * @param status Status zum Filtern
     * @return Gefilterte Liste
     */
    @GetMapping(value = "/outgoing/by-status/{status}")
    ResponseEntity<List<CustomsRequestDto>> getOutgoingRequestsByStatus(@PathVariable RequestStatus status);

    // ==================== Eingehende Anfragen (als Lieferant) ====================

    /**
     * Alle eingehenden Anfragen abrufen.
     * @return Liste der eingehenden Anfragen
     */
    @GetMapping(value = "/incoming")
    ResponseEntity<List<CustomsRequestDto>> getIncomingRequests();

    /**
     * Eingehende Anfragen nach Status filtern.
     * @param status Status zum Filtern
     * @return Gefilterte Liste
     */
    @GetMapping(value = "/incoming/by-status/{status}")
    ResponseEntity<List<CustomsRequestDto>> getIncomingRequestsByStatus(@PathVariable RequestStatus status);

    /**
     * Simuliert den Empfang einer eingehenden Anfrage (vom EDC).
     * Wird für Tests verwendet, um eingehende Anfragen zu erstellen.
     * @param request Eingehende Anfragedaten
     * @return Erstellte eingehende Anfrage
     */
    @PostMapping(value = "/receive", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CustomsRequestDto> receiveRequest(@RequestBody ReceiveRequestDto request);

    /**
     * Eingehende Anfrage in Bearbeitung nehmen.
     * Ändert Status zu IN_PROGRESS.
     * @param id Anfrage-ID
     * @return Aktualisierte Anfrage
     */
    @PostMapping(value = "/{id}/accept")
    ResponseEntity<CustomsRequestDto> acceptRequest(@PathVariable Long id);

    /**
     * Anfrage beantworten (LLE-Daten senden).
     * Erstellt eine CustomsNotification mit den angefragten Daten.
     * @param id Anfrage-ID
     * @param lleIds IDs der zu sendenden LLEs
     * @return Beantwortete Anfrage
     */
    @PostMapping(value = "/{id}/answer")
    ResponseEntity<CustomsRequestDto> answerRequest(
            @PathVariable Long id,
            @RequestBody List<Long> lleIds);

    /**
     * Anfrage ablehnen.
     * @param id Anfrage-ID
     * @param reason Ablehnungsgrund
     * @return Abgelehnte Anfrage
     */
    @PostMapping(value = "/{id}/reject")
    ResponseEntity<CustomsRequestDto> rejectRequest(
            @PathVariable Long id,
            @RequestParam String reason);

    // ==================== Status & Statistiken ====================

    /**
     * Überfällige Anfragen abrufen.
     * @return Liste überfälliger Anfragen
     */
    @GetMapping(value = "/overdue")
    ResponseEntity<List<CustomsRequestDto>> getOverdueRequests();

    /**
     * Status einer Anfrage aktualisieren (Admin).
     * @param id Anfrage-ID
     * @param status Neuer Status
     * @return Aktualisierte Anfrage
     */
    @PatchMapping(value = "/{id}/status")
    ResponseEntity<CustomsRequestDto> updateRequestStatus(
            @PathVariable Long id,
            @RequestParam RequestStatus status);

    /**
     * Request-Statistiken für Dashboard.
     * @return Statistik-DTO
     */
    @GetMapping(value = "/stats")
    ResponseEntity<RequestStatsDto> getRequestStats();

    /**
     * DTO für Request-Statistiken.
     */
    record RequestStatsDto(
            long outgoingTotal,
            long outgoingOpen,
            long outgoingAnswered,
            long outgoingOverdue,
            long incomingTotal,
            long incomingOpen,
            long incomingRequiresAction
    ) {}
}
