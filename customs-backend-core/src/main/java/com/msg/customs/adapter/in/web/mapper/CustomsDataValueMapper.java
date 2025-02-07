package com.msg.customs.adapter.in.web.mapper;

import com.msg.customs.adapter.in.web.model.CustomsDataDto;
import com.msg.customs.application.domain.model.ContactData;
import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.domain.model.Part;
import com.msg.customs.application.domain.model.TradeWorth;
import com.msg.customs.model.CustomsTerritoryEnum;
import com.msg.customs.model.GermanFederalStateEnum;

public class CustomsDataValueMapper {

    public static CustomsDataDto toValue(CustomsData data) {
        CustomsDataDto dto = new CustomsDataDto();
        dto.setId(data.getId());
        dto.setCatenaxId(data.getCatenaxId());
        dto.setCustomerPartNumber(data.getCustomerPart().getNumber());
        dto.setCustomerPartName(data.getCustomerPart().getName());
        dto.setManufacturerPartNumber(data.getManufacturePart().getNumber());
        dto.setManufacturerPartName(data.getManufacturePart().getName());
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
        dto.setPrefferedStatus(data.getPrefferedStatus());
        dto.setTradeWorthValue(data.getTradeWorth().getValue());
        dto.setTradeWorthCurrency(data.getTradeWorth().getCurrency());
        return dto;
    }

    public static CustomsData toDomain(CustomsDataDto dto) {
        CustomsData data = new CustomsData();
        data.setId(dto.getId());
        data.setCatenaxId(dto.getCatenaxId());

        Part customerPart = new Part();
        customerPart.setNumber(dto.getCustomerPartNumber());
        customerPart.setName(dto.getCustomerPartName());
        data.setCustomerPart(customerPart);

        Part manufacturePart = new Part();
        manufacturePart.setNumber(dto.getManufacturerPartNumber());
        manufacturePart.setName(dto.getManufacturerPartName());
        data.setManufacturePart(manufacturePart);

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
        data.setPrefferedStatus(dto.getPrefferedStatus());

        TradeWorth tradeWorth = new TradeWorth();
        tradeWorth.setValue(dto.getTradeWorthValue());
        tradeWorth.setCurrency(dto.getTradeWorthCurrency());
        data.setTradeWorth(tradeWorth);

        return data;
    }
}