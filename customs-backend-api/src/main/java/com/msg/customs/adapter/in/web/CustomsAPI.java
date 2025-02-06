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

import com.msg.customs.adapter.in.web.model.CustomsDataDto;

@RequestMapping(value = "/customs", produces = MediaType.APPLICATION_JSON_VALUE)
public interface CustomsAPI {

	
	@GetMapping
	ResponseEntity<List<CustomsDataDto>> getCustomsData();
	
	@GetMapping(value = "/{id}")
	ResponseEntity<CustomsDataDto> getCustomsData(@PathVariable String id);
	
	@DeleteMapping(value = "/{id}")
	ResponseEntity<CustomsDataDto> deleteCustomsData(@PathVariable String catenaxId);
	
	@PostMapping
	ResponseEntity<Void> saveCustomsData(@RequestBody CustomsDataDto customsData);
		
}
