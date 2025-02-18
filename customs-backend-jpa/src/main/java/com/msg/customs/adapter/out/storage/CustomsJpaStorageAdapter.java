package com.msg.customs.adapter.out.storage;

import java.util.List;

import com.msg.customs.adapter.out.storage.mapper.CustomsDataEntityMapper;
import com.msg.customs.adapter.out.storage.model.CustomsEntity;
import com.msg.customs.adapter.out.storage.repository.CustomsRepository;
import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.port.out.CustomsDataStorageAdapter;
import com.msg.customs.common.config.Adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Adapter
public class CustomsJpaStorageAdapter implements CustomsDataStorageAdapter {

	private final CustomsRepository customsRepository;
	
	@Override
	public void saveCustomsData(CustomsData customsData) {
		CustomsEntity customsEntity = CustomsDataEntityMapper.toEntity(customsData);
		
		log.info(customsEntity.toString());
		
        customsRepository.save(customsEntity);		
	}

	@Override
	public List<CustomsData> loadCustomsData() {
		List<CustomsEntity> allCustoms = customsRepository.findAll();
		return allCustoms.stream().map(CustomsDataEntityMapper::toDomain).toList();
	}

	@Override
	public CustomsData loadCustomsDataByCatenaxId(String catenaxId) {
		return CustomsDataEntityMapper.toDomain(customsRepository.findByCatenaxId(catenaxId));
	}

	@Override
	public List<CustomsData> loadCustomsDataByCatenaxIds(List<String> catenaxIds) {
		List<CustomsEntity> data = customsRepository.findByCatenaxIdIn(catenaxIds);
		return data.stream().map(CustomsDataEntityMapper::toDomain).toList();
	}
	
	

}
