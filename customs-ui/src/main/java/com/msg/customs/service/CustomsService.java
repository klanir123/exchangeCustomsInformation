package com.msg.customs.service;

import java.util.List;
import java.util.Map;

import com.msg.customs.adapter.in.web.model.MasterPartDto;
import com.msg.customs.domain.UiMasterPart;
import com.msg.customs.domain.UiTask;
import com.msg.customs.mapper.UiMasterPartMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.msg.customs.adapter.in.web.model.CustomsDataDto;
import com.msg.customs.config.CustomsBackendProperties;
import com.msg.customs.domain.UiCustomsData;
import com.msg.customs.domain.UiCustomsReducedData;
import com.msg.customs.mapper.UiCustomsMapper;
import com.msg.customs.mapper.UiCustomsReducedMapper;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class CustomsService {
	
	private final RestTemplate restTemplate;
	
	private final CustomsBackendProperties backendProperties;

	public List<String> getCustomsCatenaxIds() {
		
		try {
			ResponseEntity<List<CustomsDataDto>> customsEntities = restTemplate.exchange(
					backendProperties.customsListUrl(), 
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<CustomsDataDto>>() {}
					);
			
			return customsEntities.getBody().stream().map(CustomsDataDto::getCatenaxId).toList();				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return List.of();
	}
		
	public List<UiCustomsReducedData> getCustoms() {
		
		try {
			ResponseEntity<List<CustomsDataDto>> customsEntities = restTemplate.exchange(
					backendProperties.customsListUrl(), 
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<CustomsDataDto>>() {}
					);
			
			return customsEntities.getBody().stream().map(UiCustomsReducedMapper::toUiData).toList();				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return List.of();
	}

	public List<UiMasterPart> getMasterParts() {

		try {
			ResponseEntity<List<MasterPartDto>> masterPartEntities = restTemplate.exchange(
					backendProperties.partListUrl(),
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<MasterPartDto>>() {}
			);

			return masterPartEntities.getBody().stream().map(UiMasterPartMapper::toUiData).toList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return List.of();




		// Test Master Part
		// UiMasterPart testMasterPart = new UiMasterPart();
		//testMasterPart.setId(123L);
		//testMasterPart.setCatenaxId("X-123456789");
		// testMasterPart.setCustomerPartName("C-Test");
		// testMasterPart.setManufacturerPartName("M-Test");
		// testMasterPart.setManufacturerPartNumber("M-Test-123");
		// testMasterPart.setCustomerPartNumber("C-Test-123");
		// UiMasterPart testMasterPart2 = new UiMasterPart();
		// testMasterPart2.setId(123L);
		// testMasterPart2.setCatenaxId("Y-987654321");
		// testMasterPart2.setCustomerPartName("K-Test1");
		// testMasterPart2.setManufacturerPartName("K-Test1");
		// testMasterPart2.setManufacturerPartNumber("L-Test-456");
		// testMasterPart2.setCustomerPartNumber("FRT-Test-456");
		//List<UiMasterPart> masterpartList = List.of(testMasterPart, testMasterPart2);

		// return masterpartList;
	}
	
	public UiCustomsData getCustomsData(String catenaxId) {
		ResponseEntity<CustomsDataDto> entity = restTemplate.getForEntity(backendProperties.customsEditUrl(), CustomsDataDto.class, Map.of("id", catenaxId));
		return UiCustomsMapper.toUiData(entity.getBody());
	}
	
	public void saveCustoms(UiCustomsData uiCustoms) {		
		CustomsDataDto customsData = UiCustomsMapper.toDto(uiCustoms);		
		restTemplate.postForEntity(backendProperties.customsUpdatetUrl(), customsData, Void.class);
	}

	public List<UiTask> getTasks(){
		// Test Task
		UiTask testTask = new UiTask();
		testTask.setId(1L);
		testTask.setCustomerPartNumber("1");
		testTask.setCustomerPartName("1");
		testTask.setManufacturerPartNumber("1");
		testTask.setManufacturerPartName("1");

		UiTask testTask2 = new UiTask();
		testTask2.setId(2L);
		testTask2.setCustomerPartNumber("2");
		testTask2.setCustomerPartName("2");
		testTask2.setManufacturerPartNumber("2");
		testTask2.setManufacturerPartName("2");
		return List.of(testTask, testTask2);


	}



	public void saveMasterPart(UiMasterPart uiMasterPart){
		MasterPartDto masterPartDto = UiMasterPartMapper.toDto(uiMasterPart);
		ResponseEntity<Void> voidResponseEntity = restTemplate.postForEntity(backendProperties.customsMasterPartUpdatetUrl(), masterPartDto, Void.class);
	}

	public void notifyCustomsDataChanged(List<String> customs) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		HttpEntity<List<String>> entity = new HttpEntity<>(customs, headers);
		restTemplate.exchange(backendProperties.customsNotifyUrl(), HttpMethod.GET, entity, Void.class);		
	}
			
}
