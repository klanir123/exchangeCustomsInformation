package com.msg.customs.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.msg.customs.adapter.in.web.model.CustomsRequestDto;
import com.msg.customs.adapter.in.web.model.SnrSupplierLinkDto;
import com.msg.customs.adapter.in.web.model.SupplierDto;
import com.msg.customs.domain.UiCustomsReducedData;
import com.msg.customs.domain.UiMasterPart;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.msg.customs.domain.NotifyRequest;
import com.msg.customs.domain.UiCustomsData;
import com.msg.customs.model.CertifiedEnum;
import com.msg.customs.model.CustomsTerritoryEnum;
import com.msg.customs.model.CustomsTypeEnum;
import com.msg.customs.model.GermanFederalStateEnum;
import com.msg.customs.service.CustomsService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CustomsController {

	private static final String BACKEND_URL = "http://localhost:8080";

	private final CustomsService customsService;
	private final RestTemplate restTemplate;
	
	
	@GetMapping("/customs")
	public String listCustomsData(Model model) {
		model.addAttribute("customsList", customsService.getCustoms());		
		return "customs";
	}

	@GetMapping("/parts")
	public String listPartsData(Model model) {
		model.addAttribute("masterpartList", customsService.getMasterParts());
		return "parts";
	}

	@GetMapping("/tasks")
	public String listTasks(Model model) {
		// Eingehende Anfragen laden (RequestCustomsInformation)
		try {
			ResponseEntity<List<CustomsRequestDto>> incomingRequests = restTemplate.exchange(
				BACKEND_URL + "/requests/incoming",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<CustomsRequestDto>>() {}
			);
			List<CustomsRequestDto> requests = incomingRequests.getBody();
			model.addAttribute("incomingRequests", requests != null ? requests : List.of());

			// Überfällige Anfragen zählen
			long overdueCount = requests != null ? requests.stream()
				.filter(r -> r.isOverdue())
				.count() : 0;
			model.addAttribute("overdueCount", overdueCount);
		} catch (Exception e) {
			log.warn("Fehler beim Laden der eingehenden Anfragen: {}", e.getMessage());
			model.addAttribute("incomingRequests", List.of());
			model.addAttribute("overdueCount", 0);
		}

		return "tasks";
	}

	@GetMapping("/lle-tasks")
	public String listLleTasks(Model model) {
		model.addAttribute("taskList", customsService.getTasks());
		return "lleTasks";
	}
	
	
	@GetMapping("/customs/edit/{id}")
	public String editCustomsData(@PathVariable String id, Model model) {				
		UiCustomsData customs = customsService.getCustomsData(id);
		prepareModelForEditOrCreate(model, customs);
		
		return "customsEdit";
	}

	@GetMapping("/customs/create")
	public String createCustomsData(Model model) {
		UiCustomsData customs = new UiCustomsData();
		customs.setValidFrom(LocalDate.now());
		prepareModelForEditOrCreate(model, customs);
		UiMasterPart masterPart = new UiMasterPart();
		prepareModelForEditOrCreate(model, masterPart);

		return "customsEdit";
	}

	@PostMapping("/customs/doCreateOrUpdate")
	public String doCreateOrUpdateCustomsData(Model model, UiCustomsData customs) {

		log.info(customs.toString());
		customsService.saveCustoms(customs);

		return "redirect:/customs";
	}

	@GetMapping("/customs/part/edit/{id}")
	public String editPartData(@PathVariable String id, Model model) {

		UiMasterPart masterPart = customsService.getMasterPartData(id);
		prepareModelForEditOrCreate(model, masterPart);

		return "customsPartEdit";
	}

	@GetMapping("/customs/part/create")
	public String createPartData(Model model) {
		UiMasterPart masterPart = new UiMasterPart();
		prepareModelForEditOrCreate(model, masterPart);

		return "customsPartEdit";
	}

	@PostMapping("/customs/part/save")
	public String saveMasterData(Model model, UiMasterPart masterPart) {

		log.info(masterPart.toString());
		customsService.saveMasterPart(masterPart);
		return "redirect:/parts";
		//return "redirect:/customs/part/create";
	}

	@GetMapping("/customs/part/delete")
	public String deleteParts(@RequestParam String ids) {
		List<String> catenaxIds = Arrays.asList(ids.split(","));
		log.info("Deleting master parts with catenaxIds: {}", catenaxIds);
		customsService.deleteMasterParts(catenaxIds);
		return "redirect:/parts";
	}

	@PostMapping("/customs/addOriginInformation/{originInformation}")
	public String addOriginInformation(Model model, UiCustomsData customs, @PathParam("originInformation") String originInformation) {

		customs.getOriginInformations().add(originInformation);

		return "customsEdit::#originInformationItems";
	}

    @PostMapping("/customs/notify")
    public ResponseEntity<Void> notifySelected(@RequestBody NotifyRequest notifyRequest) {
        List<String> catenaxIds = notifyRequest.getCatenaxIds();
        customsService.notifyCustomsDataChanged(catenaxIds);
        return ResponseEntity.ok().build();
    }

	private void prepareModelForEditOrCreate(Model model, UiCustomsData customs) {
		model.addAttribute("customs", customs);
		model.addAttribute("types", CustomsTypeEnum.values());
		model.addAttribute("countries", CustomsTerritoryEnum.values());
		model.addAttribute("federalStates", GermanFederalStateEnum.values());
		model.addAttribute("certifiedOptions", CertifiedEnum.values());
	}

	private void prepareModelForEditOrCreate(Model model, UiMasterPart masterPart) {
		model.addAttribute("masterPart", masterPart);
		model.addAttribute("masterpartList", customsService.getMasterParts());

		// Lieferanten-Verknüpfungen laden (nur bei bestehendem Produkt)
		if (masterPart.getId() != null) {
			loadSuppliersForMasterPart(model, masterPart.getId());
		}
	}

	/**
	 * Lädt die verknüpften und verfügbaren Lieferanten für ein Produkt.
	 * Implementiert FR-2.3: Zuordnung SNR zu Lieferant.
	 */
	private void loadSuppliersForMasterPart(Model model, Long masterpartId) {
		try {
			// Verknüpfte Lieferanten laden
			ResponseEntity<List<SnrSupplierLinkDto>> linkedResponse = restTemplate.exchange(
				BACKEND_URL + "/customs/parts/" + masterpartId + "/suppliers",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<SnrSupplierLinkDto>>() {}
			);
			List<SnrSupplierLinkDto> linkedSuppliers = linkedResponse.getBody();
			model.addAttribute("linkedSuppliers", linkedSuppliers);

			// Alle aktiven Lieferanten laden
			ResponseEntity<List<SupplierDto>> allSuppliersResponse = restTemplate.exchange(
				BACKEND_URL + "/suppliers/active",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<SupplierDto>>() {}
			);
			List<SupplierDto> allActiveSuppliers = allSuppliersResponse.getBody();

			// Bereits verknüpfte Lieferanten herausfiltern
			Set<String> linkedBpnls = linkedSuppliers != null
				? linkedSuppliers.stream().map(SnrSupplierLinkDto::getSupplierBpnl).collect(Collectors.toSet())
				: Set.of();

			List<SupplierDto> availableSuppliers = allActiveSuppliers != null
				? allActiveSuppliers.stream()
					.filter(s -> !linkedBpnls.contains(s.getBpnl()))
					.collect(Collectors.toList())
				: List.of();

			model.addAttribute("availableSuppliers", availableSuppliers);

		} catch (Exception e) {
			log.warn("Fehler beim Laden der Lieferanten für Masterpart {}: {}", masterpartId, e.getMessage());
			model.addAttribute("linkedSuppliers", List.of());
			model.addAttribute("availableSuppliers", List.of());
		}
	}

}
