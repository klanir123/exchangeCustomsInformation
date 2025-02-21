package com.msg.customs.mapper;

import com.msg.customs.adapter.in.web.model.CustomsDataDto;
import com.msg.customs.domain.UiCustomsData;
import com.msg.customs.model.CertifiedEnum;
import com.msg.customs.model.RequestStatusEnum;

public class UiCustomsMapper {
	
	public static UiCustomsData toUiData(CustomsDataDto dto) {
		UiCustomsData data = new UiCustomsData();
		
		data.setId(dto.getId());
		
		data.setCatenaxId(dto.getCatenaxId());
		
		if (RequestStatusEnum.Complete == dto.getRequestStatus()) {
			data.setCompleted(true);
		}
		else {
			data.setCompleted(false);
		}
		
		data.setCustomerPartNumber(dto.getCustomerPartNumber());
		data.setCustomerPartName(dto.getCustomerPartName());
		data.setManufacturerPartNumber(dto.getManufacturerPartNumber());
		data.setManufacturerPartName(dto.getManufacturerPartName());
		
		// TODO setConfirmationDate
				
		data.setValidFrom(dto.getValidFrom());

		data.setType(dto.getType());
		
		data.setContactName(dto.getContactName());
		data.setContactEmail(dto.getContactEmail());
		data.setContactPhone(dto.getContactPhone());
		data.setContactJobTitle(dto.getContactJobTitle());
		
		data.setCountry(dto.getCountry());
		data.setFederalState(dto.getFederalState());
		data.setOriginInformations(dto.getOriginInformation());
				
		data.setPrefferedStatus(dto.getPrefferedStatus());
		if (data.getPrefferedStatus() == null) {
            data.setPrefferedStatus(CertifiedEnum.N.name());
		}
            
		data.setPrefferedStatus(dto.getPrefferedStatus());
		if (data.getPrefferedStatus() == null) {
			data.setPrefferedStatus(CertifiedEnum.N.name());
		}
		
		data.setTradeWorthValue(dto.getTradeWorthValue());
		data.setTradeWorthCurrency(dto.getTradeWorthCurrency());
		
		return data;
	}
	
	public static CustomsDataDto toDto(UiCustomsData data) {
		CustomsDataDto dto = new CustomsDataDto();
		
		dto.setId(data.getId());
		
		dto.setCatenaxId(data.getCatenaxId());
		if (data.isCompleted()) {
            dto.setRequestStatus(RequestStatusEnum.Complete);
        }
        else {
			dto.setRequestStatus(RequestStatusEnum.NotComplete);
		}
		
		dto.setCustomerPartNumber(data.getCustomerPartNumber());
		dto.setCustomerPartName(data.getCustomerPartName());
		dto.setManufacturerPartNumber(data.getManufacturerPartNumber());
		dto.setManufacturerPartName(data.getManufacturerPartName());
		
		
		// TODO setConfirmationDate -> Should this been done in the UI or in the backend during save?
		
		dto.setValidFrom(data.getValidFrom());
		dto.setType(data.getType());
		
		dto.setContactName(data.getContactName());
		dto.setContactEmail(data.getContactEmail());
		dto.setContactPhone(data.getContactPhone());
		dto.setContactJobTitle(data.getContactJobTitle());
		
		dto.setCountry(data.getCountry());
		dto.setFederalState(data.getFederalState());
		dto.setOriginInformation(data.getOriginInformations());
		
		dto.setPrefferedStatus(data.getPrefferedStatus());
		if (dto.getPrefferedStatus() == null) {
			dto.setPrefferedStatus(CertifiedEnum.N.name());
		}
		
		dto.setTradeWorthValue(data.getTradeWorthValue());
		dto.setTradeWorthCurrency(data.getTradeWorthCurrency());
				
		return dto;
	}
	

}
