package com.msg.customs.controller;

import java.time.LocalDate;
import java.util.List;

import com.msg.customs.domain.UiCustomsReducedData;
import com.msg.customs.domain.UiMasterPart;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
	
	private final CustomsService customsService;
	
	
	@GetMapping("/customs")
	public String listCustomsData(Model model) {
		model.addAttribute("customsList", customsService.getCustoms());		
		return "customs";
	}

	@GetMapping("/parts")
	public String listPartsData(Model model) {
		model.addAttribute("master partList", customsService.getMasterParts());
		return "parts";
	}

	@GetMapping("/tasks")
	public String listTasks(Model model) {
		model.addAttribute("taskList", customsService.getTasks());
		return "tasks";
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
		// TODO: implement service to get parts data by id
		// UiMasterPart masterPart = customsService.getMsertPartData(id);
		UiMasterPart masterPart = new UiMasterPart();
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
		return "redirect:/customs/part/create";
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
	}

}
