package com.msg.customs.mapper;

import com.msg.customs.adapter.in.web.model.CustomsDataDto;
import com.msg.customs.domain.UiCustomsReducedData;

public class UiCustomsReducedMapper {

	
	public static UiCustomsReducedData toUiData(CustomsDataDto dto) {
		UiCustomsReducedData data = new UiCustomsReducedData();
		
		data.setId(dto.getId());
		
		data.setCatenaxId(dto.getCatenaxId());
		
		data.setCustomerPartNumber(dto.getCustomerPartNumber());
		data.setCustomerPartName(dto.getCustomerPartName());
				
		return data;
	}	
	
}
