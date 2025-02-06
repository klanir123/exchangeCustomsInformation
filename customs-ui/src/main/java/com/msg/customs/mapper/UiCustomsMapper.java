package com.msg.customs.mapper;

import java.util.Date;
import java.util.List;

import com.msg.customs.adapter.in.web.model.CustomsDataValue;
import com.msg.customs.domain.UiCustomsData;

public class UiCustomsMapper {
	
	public static UiCustomsData toUiData(CustomsDataValue value) {
		UiCustomsData data = new UiCustomsData();
		
		data.setCatenaxId(value.getCatenaxId());
		
		data.setCustomerPartNumber(value.getCustomerPartNumber());
		data.setCustomerPartName(value.getCustomerPartName());
		data.setManufacturerPartNumber(value.getManufacturerPartNumber());
		data.setManufacturerPartName(value.getManufacturerPartName());
		
		// TODO setConfirmationDate
				
		data.setValidFrom(null);
		data.setType(value.getType());
		
		data.setContactName(value.getContactName());
		data.setContactEmail(value.getContactEmail());
		data.setContactPhone(value.getContactPhone());
		data.setContactJobTitle(value.getContactJobTitle());
		
		data.setCountry(value.getCountry());
		data.setFederalState(value.getFederalState());
		data.setOriginInformation(value.getOriginInformation().get(0));
		
		data.setPrefferedStatus(value.getPrefferedStatus());
		
		data.setTradeWorthValue(value.getTradeWorthValue());
		data.setTradeWorthCurrency(value.getTradeWorthCurrency());
		
		return data;
	}
	
	public static CustomsDataValue toValue(UiCustomsData data) {
		CustomsDataValue value = new CustomsDataValue();
		
		value.setCatenaxId(data.getCatenaxId());
		
		value.setCustomerPartNumber(data.getCustomerPartNumber());
		value.setCustomerPartName(data.getCustomerPartName());
		value.setManufacturerPartNumber(data.getManufacturerPartNumber());
		value.setManufacturerPartName(data.getManufacturerPartName());
		
		
		// TODO setConfirmationDate
		
		// TODO: map to date
		value.setValidFrom(new Date());
		value.setType(data.getType());
		
		value.setContactName(data.getContactName());
		value.setContactEmail(data.getContactEmail());
		value.setContactPhone(data.getContactPhone());
		value.setContactJobTitle(data.getContactJobTitle());
		
		value.setCountry(data.getCountry());
		value.setFederalState(data.getFederalState());
		value.setOriginInformation(List.of(data.getOriginInformation()));
		
		value.setPrefferedStatus(data.getPrefferedStatus());
		
		value.setTradeWorthValue(data.getTradeWorthValue());
		value.setTradeWorthCurrency(data.getTradeWorthCurrency());
				
		return value;
	}
	

}
