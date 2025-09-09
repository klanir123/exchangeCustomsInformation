package com.msg.customs.adapter.in.web.mapper;

import com.msg.customs.adapter.in.web.model.CustomsDataDto;
import com.msg.customs.adapter.in.web.model.MasterPartDto;
import com.msg.customs.application.domain.model.*;
import com.msg.customs.model.CertifiedEnum;
import com.msg.customs.model.CustomsTerritoryEnum;
import com.msg.customs.model.GermanFederalStateEnum;

/**
 * Mapper to map the Dto to the internal customs data representation.
 */
public class MasterPartValueMapper {

	/**
	 * Maps the internal customs data to the Dto.
	 * @param data the customs data
	 * @return the dto.	
	 */
    public static MasterPartDto toDto(MasterPart data) {
        MasterPartDto dto = new MasterPartDto();
        dto.setId(data.getId());
        dto.setCatenaxId(data.getCatenaXId());
        dto.setCustomerPartNumber(data.getCustomerPartNumber());
        dto.setCustomerPartName(data.getCustomerPartName());
        dto.setManufacturerPartNumber(data.getManufacturerPartNumber());
        dto.setManufacturerPartName(data.getManufacturerPartName());
        return dto;
    }

	/**
	 * Maps the Dto to the internal customs data.
	 * 
	 * @param dto the dto
	 * @return the customs data.
	 */
    public static MasterPart toDomain(MasterPartDto dto) {
        MasterPart data = new MasterPart();
        data.setId(dto.getId());
        data.setCatenaXId(dto.getCatenaxId());
        data.setCustomerPartNumber(dto.getCustomerPartNumber());
        data.setCustomerPartName(dto.getCustomerPartName());
        data.setManufacturerPartNumber(dto.getManufacturerPartNumber());
        data.setManufacturerPartName(dto.getManufacturerPartName());

        return data;
    }
}