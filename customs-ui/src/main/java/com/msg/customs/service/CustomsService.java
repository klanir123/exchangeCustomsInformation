package com.msg.customs.service;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.msg.customs.adapter.in.web.model.CustomsDataDto;
import com.msg.customs.domain.UiCustomsData;
import com.msg.customs.domain.UiCustomsReducedData;
import com.msg.customs.mapper.UiCustomsMapper;
import com.msg.customs.mapper.UiCustomsReducedMapper;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class CustomsService {
	
	private final RestTemplate restTemplate;
	

	public List<UiCustomsReducedData> getCustoms() {
		
		ResponseEntity<List<CustomsDataDto>> customsEntities = restTemplate.exchange(
				"http://localhost:8080/customs", 
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<CustomsDataDto>>() {}
			);
		
		return customsEntities.getBody().stream().map(UiCustomsReducedMapper::toUiData).toList();		
	}
	
	public UiCustomsData getCustomsData(String catenaxId) {
		ResponseEntity<CustomsDataDto> entity = restTemplate.getForEntity("http://localhost:8080/customs/{id}", CustomsDataDto.class, Map.of("id", catenaxId));
		return UiCustomsMapper.toUiData(entity.getBody());
	}
	
	public void saveCustoms(UiCustomsData uiCustoms) {		
		CustomsDataDto customsData = UiCustomsMapper.toDto(uiCustoms);		
		restTemplate.postForEntity("http://localhost:8080/customs", customsData, Void.class);
	}
			
}
