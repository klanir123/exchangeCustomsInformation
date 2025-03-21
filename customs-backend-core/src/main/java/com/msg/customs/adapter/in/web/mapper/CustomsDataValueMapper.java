package com.msg.customs.adapter.in.web.mapper;

import com.msg.customs.adapter.in.web.model.CustomsDataDto;
import com.msg.customs.application.domain.model.ContactData;
import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.domain.model.Part;
import com.msg.customs.application.domain.model.TracedWorth;
import com.msg.customs.model.CertifiedEnum;
import com.msg.customs.model.CustomsTerritoryEnum;
import com.msg.customs.model.GermanFederalStateEnum;

/**
 * Mapper to map the Dto to the internal customs data representation.
 */
public class CustomsDataValueMapper {

	/**
	 * Maps the internal customs data to the Dto.
	 * @param data the customs data
	 * @return the dto.	
	 */
    public static CustomsDataDto toDto(CustomsData data) {
        CustomsDataDto dto = new CustomsDataDto();
        dto.setId(data.getId());
        dto.setCatenaxId(data.getCatenaxId());
        dto.setRequestStatus(data.getRequestStatus());
        dto.setCustomerPartNumber(data.getCustomerPart().getNumber());
        dto.setCustomerPartName(data.getCustomerPart().getName());
        dto.setManufacturerPartNumber(data.getManufacturerPart().getNumber());
        dto.setManufacturerPartName(data.getManufacturerPart().getName());
        dto.setType(data.getType());
        dto.setConfirmationDate(data.getConfirmationDate());
        dto.setValidFrom(data.getValidFrom());
        dto.setContactName(data.getContact().getName());
        dto.setContactEmail(data.getContact().getEmail());
        dto.setContactPhone(data.getContact().getPhone());
        dto.setContactJobTitle(data.getContact().getJobTitle());
        dto.setCountry(data.getCountry() != null ? data.getCountry().getCode() : null);
        dto.setFederalState(data.getFederalState() != null ? data.getFederalState().getCode() : null);
        dto.setOriginInformation(data.getOriginInformation());
        dto.setPrefferedStatus(data.getPreferredStatus().name());
        dto.setTracedWorthValue(data.getTracedWorth().getValue());
        dto.setTracedWorthCurrency(data.getTracedWorth().getCurrency());
        return dto;
    }

	/**
	 * Maps the Dto to the internal customs data.
	 * 
	 * @param dto the dto
	 * @return the customs data.
	 */
    public static CustomsData toDomain(CustomsDataDto dto) {
        CustomsData data = new CustomsData();
        data.setId(dto.getId());
        data.setCatenaxId(dto.getCatenaxId());
        data.setRequestStatus(dto.getRequestStatus());
        
        Part customerPart = new Part();
        customerPart.setNumber(dto.getCustomerPartNumber());
        customerPart.setName(dto.getCustomerPartName());
        data.setCustomerPart(customerPart);

        Part manufacturePart = new Part();
        manufacturePart.setNumber(dto.getManufacturerPartNumber());
        manufacturePart.setName(dto.getManufacturerPartName());
        data.setManufacturerPart(manufacturePart);

        data.setType(dto.getType());
        data.setConfirmationDate(dto.getConfirmationDate());
        data.setValidFrom(dto.getValidFrom());

        ContactData contact = new ContactData();
        contact.setName(dto.getContactName());
        contact.setEmail(dto.getContactEmail());
        contact.setPhone(dto.getContactPhone());
        contact.setJobTitle(dto.getContactJobTitle());
        data.setContact(contact);

        data.setCountry(dto.getCountry() != null ? CustomsTerritoryEnum.valueOf(dto.getCountry()) : null);
        data.setFederalState(dto.getFederalState() != null ? GermanFederalStateEnum.valueOf(dto.getFederalState()) : null);
        data.setOriginInformation(dto.getOriginInformation());
        data.setPreferredStatus(dto.getPrefferedStatus() != null ? CertifiedEnum.valueOf(dto.getPrefferedStatus()) : CertifiedEnum.N);

        TracedWorth tracedWorth = new TracedWorth();
        tracedWorth.setValue(dto.getTracedWorthValue());
        tracedWorth.setCurrency(dto.getTracedWorthCurrency());
        data.setTracedWorth(tracedWorth);

        return data;
    }
}