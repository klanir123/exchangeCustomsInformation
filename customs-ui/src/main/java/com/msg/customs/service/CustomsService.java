package com.msg.customs.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.msg.customs.adapter.in.web.model.CustomsDataValue;
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
		
		ResponseEntity<List<CustomsDataValue>> customsEntities = restTemplate.exchange(
				"http://localhost:8080/customs", 
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<CustomsDataValue>>() {}
			);
		
		return customsEntities.getBody().stream().map(UiCustomsReducedMapper::toUiData).toList();		
	}
	
	public void saveCustoms(UiCustomsData uiCustoms) {
		
		CustomsDataValue customsData = UiCustomsMapper.toValue(uiCustoms);
		
		restTemplate.postForEntity("http://localhost:8080/customs", customsData, Void.class);
	}
			
}
