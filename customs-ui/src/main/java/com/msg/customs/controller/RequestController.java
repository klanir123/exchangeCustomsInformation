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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.msg.customs.adapter.in.web.RequestAPI.RequestStatsDto;
import com.msg.customs.adapter.in.web.model.CreateRequestDto;
import com.msg.customs.adapter.in.web.model.CustomsDataDto;
import com.msg.customs.adapter.in.web.model.CustomsRequestDto;
import com.msg.customs.adapter.in.web.model.SupplierDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * UI Controller für Request-Seiten (Epic 5).
 *
 * User Story 5.4: UI-Seiten für Anfragen
 */
@Slf4j
@Controller
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RestTemplate restTemplate;

    @Value("${customs.backend.url:http://localhost:8080}")
    private String backendUrl;

    // ==================== Ausgehende Anfragen ====================

    @GetMapping("/outgoing")
    public String showOutgoingRequests(Model model) {
        log.debug("Loading outgoing requests page");
        try {
            ResponseEntity<List<CustomsRequestDto>> response = restTemplate.exchange(
                    backendUrl + "/requests/outgoing",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<CustomsRequestDto>>() {}
            );
            model.addAttribute("requestList", response.getBody());

            // Statistiken laden
            ResponseEntity<RequestStatsDto> statsResponse = restTemplate.getForEntity(
                    backendUrl + "/requests/stats",
                    RequestStatsDto.class
            );
            model.addAttribute("stats", statsResponse.getBody());

        } catch (Exception e) {
            log.error("Error loading outgoing requests", e);
            model.addAttribute("error", "Fehler beim Laden der Anfragen: " + e.getMessage());
        }
        return "requests-outgoing";
    }

    // ==================== Eingehende Anfragen ====================

    @GetMapping("/incoming")
    public String showIncomingRequests(Model model) {
        log.debug("Loading incoming requests page");
        try {
            ResponseEntity<List<CustomsRequestDto>> response = restTemplate.exchange(
                    backendUrl + "/requests/incoming",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<CustomsRequestDto>>() {}
            );
            model.addAttribute("requestList", response.getBody());

            // Statistiken laden
            ResponseEntity<RequestStatsDto> statsResponse = restTemplate.getForEntity(
                    backendUrl + "/requests/stats",
                    RequestStatsDto.class
            );
            model.addAttribute("stats", statsResponse.getBody());

        } catch (Exception e) {
            log.error("Error loading incoming requests", e);
            model.addAttribute("error", "Fehler beim Laden der Anfragen: " + e.getMessage());
        }
        return "requests-incoming";
    }

    // ==================== Anfrage erstellen/bearbeiten ====================

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        log.debug("Loading create request form");
        loadSupplierList(model);
        model.addAttribute("request", new CreateRequestDto());
        return "requestCreate";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.debug("Loading edit form for request {}", id);
        try {
            ResponseEntity<CustomsRequestDto> response = restTemplate.getForEntity(
                    backendUrl + "/requests/" + id,
                    CustomsRequestDto.class
            );
            model.addAttribute("request", response.getBody());
            loadSupplierList(model);
        } catch (Exception e) {
            log.error("Error loading request {}", id, e);
            model.addAttribute("error", "Fehler beim Laden der Anfrage: " + e.getMessage());
        }
        return "requestCreate";
    }

    @PostMapping("/save")
    public String saveRequest(CreateRequestDto request,
                              @RequestParam(defaultValue = "save") String action,
                              RedirectAttributes redirectAttributes) {
        log.debug("Saving request, action: {}", action);
        try {
            // Anfrage speichern
            ResponseEntity<CustomsRequestDto> response = restTemplate.postForEntity(
                    backendUrl + "/requests",
                    request,
                    CustomsRequestDto.class
            );

            Long requestId = response.getBody().getId();

            // Bei "send" direkt versenden
            if ("send".equals(action)) {
                restTemplate.postForEntity(
                        backendUrl + "/requests/" + requestId + "/send",
                        null,
                        CustomsRequestDto.class
                );
                redirectAttributes.addFlashAttribute("success", "Anfrage wurde erfolgreich versendet.");
            } else {
                redirectAttributes.addFlashAttribute("success", "Anfrage wurde als Entwurf gespeichert.");
            }

            return "redirect:/requests/outgoing";

        } catch (Exception e) {
            log.error("Error saving request", e);
            redirectAttributes.addFlashAttribute("error", "Fehler beim Speichern: " + e.getMessage());
            return "redirect:/requests/create";
        }
    }

    // ==================== Anfrage beantworten ====================

    @GetMapping("/{id}/answer")
    public String showAnswerForm(@PathVariable Long id, Model model) {
        log.debug("Loading answer form for request {}", id);
        try {
            // Anfrage laden
            ResponseEntity<CustomsRequestDto> requestResponse = restTemplate.getForEntity(
                    backendUrl + "/requests/" + id,
                    CustomsRequestDto.class
            );
            model.addAttribute("request", requestResponse.getBody());

            // LLE-Liste laden (aktive LLEs)
            ResponseEntity<List<CustomsDataDto>> lleResponse = restTemplate.exchange(
                    backendUrl + "/customs",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<CustomsDataDto>>() {}
            );
            model.addAttribute("lleList", lleResponse.getBody());

        } catch (Exception e) {
            log.error("Error loading answer form for request {}", id, e);
            model.addAttribute("error", "Fehler beim Laden: " + e.getMessage());
        }
        return "requestAnswer";
    }

    // ==================== Anfrage Detail ====================

    @GetMapping("/view/{id}")
    public String showRequestDetail(@PathVariable Long id, Model model) {
        log.debug("Loading request detail for {}", id);
        try {
            ResponseEntity<CustomsRequestDto> response = restTemplate.getForEntity(
                    backendUrl + "/requests/" + id,
                    CustomsRequestDto.class
            );
            model.addAttribute("request", response.getBody());
        } catch (Exception e) {
            log.error("Error loading request {}", id, e);
            model.addAttribute("error", "Fehler beim Laden der Anfrage: " + e.getMessage());
        }
        return "requestDetail";
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
}
