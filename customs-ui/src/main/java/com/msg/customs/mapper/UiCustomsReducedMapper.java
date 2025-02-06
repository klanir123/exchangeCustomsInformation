package com.msg.customs.mapper;

import com.msg.customs.adapter.in.web.model.CustomsDataValue;
import com.msg.customs.domain.UiCustomsReducedData;

public class UiCustomsReducedMapper {

	
	public static UiCustomsReducedData toUiData(CustomsDataValue value) {
		UiCustomsReducedData data = new UiCustomsReducedData();
		
		data.setCatenaxId(value.getCatenaxId());
		
		data.setCustomerPartNumber(value.getCustomerPartNumber());
		data.setCustomerPartName(value.getCustomerPartName());
				
		return data;
	}	
	
}
