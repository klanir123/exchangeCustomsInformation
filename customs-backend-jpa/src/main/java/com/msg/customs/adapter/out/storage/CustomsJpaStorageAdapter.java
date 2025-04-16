package com.msg.customs.adapter.out.storage;

import java.util.List;

import com.msg.customs.adapter.out.storage.mapper.CustomsDataEntityMapper;
import com.msg.customs.adapter.out.storage.mapper.MasterPartEntityMapper;
import com.msg.customs.adapter.out.storage.model.CustomsEntity;
import com.msg.customs.adapter.out.storage.model.MasterPartEntity;
import com.msg.customs.adapter.out.storage.repository.CustomsRepository;
import com.msg.customs.adapter.out.storage.repository.PartRepository;
import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.domain.model.MasterPart;
import com.msg.customs.application.port.out.CustomsDataStorageAdapter;
import com.msg.customs.common.config.Adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JPA implementation of the CustomsDataStorageAdapter.
 */
@Slf4j
@RequiredArgsConstructor
@Adapter
public class CustomsJpaStorageAdapter implements CustomsDataStorageAdapter {

	private final CustomsRepository customsRepository;
	private final PartRepository partRepository;
    private MasterPart masterPartData;

    @Override
	public void saveCustomsData(CustomsData customsData) {
		CustomsEntity customsEntity = CustomsDataEntityMapper.toEntity(customsData);
		
		log.info(customsEntity.toString());
		
        customsRepository.save(customsEntity);		
	}

	@Override
    public void saveMasterPartData(MasterPart masterPartData) {
        this.masterPartData = masterPartData;
        MasterPartEntity masterPartEntity = MasterPartEntityMapper.toEntity(masterPartData);

		log.info(masterPartEntity.toString());

		partRepository.save(masterPartEntity);
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
