package com.msg.customs.adapter.out.storage.mapper;

import com.msg.customs.adapter.out.storage.model.CustomsEntity;
import com.msg.customs.application.domain.model.CustomsData;

/**
 * Mapper for customs data domain model and entity.
 */
public class CustomsDataEntityMapper {

	/**
	 * Maps the customs data domain model data to the entity. 
	 * @param data the customs data domain model
	 * @return the customs data entity
	 */
    public static CustomsEntity toEntity(CustomsData data) {
        CustomsEntity entity = new CustomsEntity();
        
        entity.setId(data.getId());
        
        entity.setCatenaxId(data.getCatenaxId());
        entity.setRequestStatus(data.getRequestStatus());

        entity.setCustomerPart(PartEntityMapper.toEntity(data.getCustomerPart()));
        entity.setManufacturePart(PartEntityMapper.toEntity(data.getManufacturerPart()));

        entity.setType(data.getType());
        entity.setConfirmationDate(data.getConfirmationDate());
        entity.setValidFrom(data.getValidFrom());
        
        entity.setContact(ContactDataEntityMapper.toEntity(data.getContact()));

        entity.setCountry(data.getCountry());
        entity.setFederalState(data.getFederalState());
        entity.setOriginInformation(data.getOriginInformation()); 
        entity.setPrefferedStatus(data.getPrefferedStatus());

        entity.setTradeWorth(TradeWorthEntityMapper.toEntity(data.getTradeWorth()));

        return entity;
    }
    
    /**
     * Maps the customs data entity to the domain model.
     * @param entity the customs data entity 
     * @return the customs data domain model
     */
    public static CustomsData toDomain(CustomsEntity entity) {
        CustomsData data = new CustomsData();
        
        data.setId(entity.getId());
        
        data.setCatenaxId(entity.getCatenaxId());
        data.setRequestStatus(entity.getRequestStatus());

        data.setCustomerPart(PartEntityMapper.toDomain(entity.getCustomerPart()));
        data.setManufacturerPart(PartEntityMapper.toDomain(entity.getManufacturePart()));

        data.setType(entity.getType());
        data.setConfirmationDate(entity.getConfirmationDate());
        data.setValidFrom(entity.getValidFrom());
        
        data.setContact(ContactDataEntityMapper.toDomain(entity.getContact()));

        data.setCountry(entity.getCountry());
        data.setFederalState(entity.getFederalState());
        data.setOriginInformation(entity.getOriginInformation());
        data.setPrefferedStatus(entity.getPrefferedStatus());

        data.setTradeWorth(TradeWorthEntityMapper.toDomain(entity.getTradeWorth()));

        return data;
    }    
}
