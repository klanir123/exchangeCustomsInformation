package com.msg.customs.controller;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.msg.customs.adapter.in.web.model.SupplierContactDto;
import com.msg.customs.adapter.in.web.model.SupplierDto;
import com.msg.customs.model.SupplierStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * UI Controller für die Lieferantenverwaltung.
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class SupplierController {

	private final RestTemplate restTemplate;

	private static final String BACKEND_URL = "http://localhost:8080";

	@GetMapping("/suppliers")
	public String listSuppliers(Model model) {
		log.debug("GET /suppliers - Loading supplier list");
		ResponseEntity<List<SupplierDto>> response = restTemplate.exchange(
			BACKEND_URL + "/suppliers",
			HttpMethod.GET,
			null,
			new ParameterizedTypeReference<List<SupplierDto>>() {}
		);
		model.addAttribute("supplierList", response.getBody());
		return "suppliers";
	}

	@GetMapping("/suppliers/create")
	public String createSupplier(Model model) {
		log.debug("GET /suppliers/create - Showing create form");
		SupplierDto supplier = new SupplierDto();
		supplier.setStatus(SupplierStatus.Active);
		prepareModelForEditOrCreate(model, supplier, true);
		return "supplierEdit";
	}

	@GetMapping("/suppliers/edit/{bpnl}")
	public String editSupplier(@PathVariable String bpnl, Model model) {
		log.debug("GET /suppliers/edit/{} - Loading supplier for edit", bpnl);
		SupplierDto supplier = restTemplate.getForObject(
			BACKEND_URL + "/suppliers/" + bpnl,
			SupplierDto.class
		);
		prepareModelForEditOrCreate(model, supplier, false);
		return "supplierEdit";
	}

	@GetMapping("/suppliers/view/{bpnl}")
	public String viewSupplier(@PathVariable String bpnl, Model model) {
		log.debug("GET /suppliers/view/{} - Loading supplier details", bpnl);
		SupplierDto supplier = restTemplate.getForObject(
			BACKEND_URL + "/suppliers/" + bpnl,
			SupplierDto.class
		);
		model.addAttribute("supplier", supplier);
		return "supplierDetail";
	}

	@PostMapping("/suppliers/save")
	public String saveSupplier(SupplierDto supplier, @RequestParam(defaultValue = "false") boolean isNew) {
		log.info("POST /suppliers/save - Saving supplier: {}", supplier.getBpnl());

		if (isNew) {
			restTemplate.postForObject(
				BACKEND_URL + "/suppliers",
				supplier,
				Void.class
			);
		} else {
			restTemplate.put(
				BACKEND_URL + "/suppliers/" + supplier.getBpnl(),
				supplier
			);
		}

		return "redirect:/suppliers";
	}

	@GetMapping("/suppliers/delete")
	public String deleteSuppliers(@RequestParam String ids) {
		log.info("GET /suppliers/delete - Deleting suppliers: {}", ids);
		String[] bpnls = ids.split(",");
		for (String bpnl : bpnls) {
			restTemplate.delete(BACKEND_URL + "/suppliers/" + bpnl.trim());
		}
		return "redirect:/suppliers";
	}

	@PostMapping("/suppliers/{bpnl}/status")
	public String updateStatus(@PathVariable String bpnl, @RequestParam SupplierStatus status) {
		log.info("POST /suppliers/{}/status - Updating status to {}", bpnl, status);
		restTemplate.patchForObject(
			BACKEND_URL + "/suppliers/" + bpnl + "/status?status=" + status,
			null,
			Void.class
		);
		return "redirect:/suppliers";
	}

	// ==================== Kontakte ====================

	@GetMapping("/suppliers/{bpnl}/contacts/add")
	public String addContactForm(@PathVariable String bpnl, Model model) {
		log.debug("GET /suppliers/{}/contacts/add - Showing contact form", bpnl);
		model.addAttribute("bpnl", bpnl);
		model.addAttribute("contact", new SupplierContactDto());
		model.addAttribute("isNew", true);
		return "supplierContactEdit";
	}

	@GetMapping("/suppliers/{bpnl}/contacts/edit/{contactId}")
	public String editContactForm(@PathVariable String bpnl, @PathVariable Long contactId, Model model) {
		log.debug("GET /suppliers/{}/contacts/edit/{} - Loading contact", bpnl, contactId);

		SupplierDto supplier = restTemplate.getForObject(
			BACKEND_URL + "/suppliers/" + bpnl,
			SupplierDto.class
		);

		SupplierContactDto contact = supplier.getContacts().stream()
			.filter(c -> c.getId().equals(contactId))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Kontakt nicht gefunden: " + contactId));

		model.addAttribute("bpnl", bpnl);
		model.addAttribute("contact", contact);
		model.addAttribute("isNew", false);
		return "supplierContactEdit";
	}

	@PostMapping("/suppliers/{bpnl}/contacts/save")
	public String saveContact(@PathVariable String bpnl, SupplierContactDto contact,
							  @RequestParam(defaultValue = "false") boolean isNew) {
		log.info("POST /suppliers/{}/contacts/save - Saving contact", bpnl);

		if (isNew) {
			restTemplate.postForObject(
				BACKEND_URL + "/suppliers/" + bpnl + "/contacts",
				contact,
				Void.class
			);
		} else {
			restTemplate.put(
				BACKEND_URL + "/suppliers/" + bpnl + "/contacts/" + contact.getId(),
				contact
			);
		}

		return "redirect:/suppliers/view/" + bpnl;
	}

	@GetMapping("/suppliers/{bpnl}/contacts/delete/{contactId}")
	public String deleteContact(@PathVariable String bpnl, @PathVariable Long contactId) {
		log.info("GET /suppliers/{}/contacts/delete/{} - Deleting contact", bpnl, contactId);
		restTemplate.delete(BACKEND_URL + "/suppliers/" + bpnl + "/contacts/" + contactId);
		return "redirect:/suppliers/view/" + bpnl;
	}

	@PostMapping("/suppliers/{bpnl}/contacts/{contactId}/primary")
	public String setContactAsPrimary(@PathVariable String bpnl, @PathVariable Long contactId) {
		log.info("POST /suppliers/{}/contacts/{}/primary - Setting as primary", bpnl, contactId);
		restTemplate.patchForObject(
			BACKEND_URL + "/suppliers/" + bpnl + "/contacts/" + contactId + "/primary",
			null,
			Void.class
		);
		return "redirect:/suppliers/view/" + bpnl;
	}

	private void prepareModelForEditOrCreate(Model model, SupplierDto supplier, boolean isNew) {
		model.addAttribute("supplier", supplier);
		model.addAttribute("statuses", SupplierStatus.values());
		model.addAttribute("isNew", isNew);
	}
}
