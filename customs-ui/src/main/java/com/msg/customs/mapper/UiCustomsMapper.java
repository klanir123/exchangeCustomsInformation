package com.msg.customs.mapper;

import java.util.Date;
import java.util.List;

import com.msg.customs.adapter.in.web.model.CustomsDataDto;
import com.msg.customs.domain.UiCustomsData;
import com.msg.customs.model.CertifiedEnum;

public class UiCustomsMapper {
	
	public static UiCustomsData toUiData(CustomsDataDto dto) {
		UiCustomsData data = new UiCustomsData();
		
		data.setId(dto.getId());
		
		data.setCatenaxId(dto.getCatenaxId());
		
		data.setCustomerPartNumber(dto.getCustomerPartNumber());
		data.setCustomerPartName(dto.getCustomerPartName());
		data.setManufacturerPartNumber(dto.getManufacturerPartNumber());
		data.setManufacturerPartName(dto.getManufacturerPartName());
		
		// TODO setConfirmationDate
				
		data.setValidFrom(null);
		// TODO use enum instead
		data.setType(dto.getType());
		
		data.setContactName(dto.getContactName());
		data.setContactEmail(dto.getContactEmail());
		data.setContactPhone(dto.getContactPhone());
		data.setContactJobTitle(dto.getContactJobTitle());
		
		data.setCountry(dto.getCountry());
		data.setFederalState(dto.getFederalState());
		data.setOriginInformation(dto.getOriginInformation().get(0));
		
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
		
		dto.setCustomerPartNumber(data.getCustomerPartNumber());
		dto.setCustomerPartName(data.getCustomerPartName());
		dto.setManufacturerPartNumber(data.getManufacturerPartNumber());
		dto.setManufacturerPartName(data.getManufacturerPartName());
		
		
		// TODO setConfirmationDate
		
		// TODO: map to date
		dto.setValidFrom(new Date());
		dto.setType(data.getType());
		
		dto.setContactName(data.getContactName());
		dto.setContactEmail(data.getContactEmail());
		dto.setContactPhone(data.getContactPhone());
		dto.setContactJobTitle(data.getContactJobTitle());
		
		dto.setCountry(data.getCountry());
		dto.setFederalState(data.getFederalState());
		dto.setOriginInformation(List.of(data.getOriginInformation()));
		
		dto.setPrefferedStatus(data.getPrefferedStatus());
		if (dto.getPrefferedStatus() == null) {
			dto.setPrefferedStatus(CertifiedEnum.N.name());
		}
		
		dto.setTradeWorthValue(data.getTradeWorthValue());
		dto.setTradeWorthCurrency(data.getTradeWorthCurrency());
				
		return dto;
	}
	

}
