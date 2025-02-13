package com.msg.customs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
		model.addAttribute("name", "Thomas");
		
		model.addAttribute("customsList", customsService.getCustoms());
		
		return "customs";
	}

	
	
	@GetMapping("/customs/edit/{id}")
	public String editCustomsData(@PathVariable String id, Model model) {				
		UiCustomsData customs = customsService.getCustomsData(id);
		prepareModelForEditOrCreate(model, customs);
		
		return "customsEdit";
	}	
	
	@GetMapping("/customs/create")
	public String createCustomsData(Model model) {
		prepareModelForEditOrCreate(model, new UiCustomsData());		
		return "customsEdit";
	}
	
	private void prepareModelForEditOrCreate(Model model, UiCustomsData customs) {
        model.addAttribute("customs", customs);
        model.addAttribute("types", CustomsTypeEnum.values());
        model.addAttribute("countries", CustomsTerritoryEnum.values());
        model.addAttribute("federalStates", GermanFederalStateEnum.values());
        model.addAttribute("certifiedOptions", CertifiedEnum.values());
    }

	@PostMapping("/customs/doCreateOrUpdate")
	public String doCreateOrUpdateCustomsData(Model model, UiCustomsData customs) {
		
		log.info(customs.toString());
		customsService.saveCustoms(customs);
		
		return "redirect:/customs";
	}
	
	@PostMapping("/customs/addOriginInformation/{originInformation}")
	public String addOriginInformation(Model model, UiCustomsData customs, @PathParam("originInformation") String originInformation) {
		
		customs.getOriginInformations().add(originInformation);
		
		return "customsEdit::#originInformationItems";
	}
	

}
