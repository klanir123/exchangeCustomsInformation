package com.msg.customs.adapter.in.web.mapper;

import com.msg.customs.adapter.in.web.model.CustomsDataValue;
import com.msg.customs.application.domain.model.ContactData;
import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.domain.model.Part;
import com.msg.customs.application.domain.model.TradeWorth;

public class CustomsDataValueMapper {

    public static CustomsDataValue toValue(CustomsData data) {
        CustomsDataValue value = new CustomsDataValue();
        value.setCatenaxId(data.getCatenaxId());
        value.setCustomerPartNumber(data.getCustomerPart().getNumber());
        value.setCustomerPartName(data.getCustomerPart().getName());
        value.setManufacturerPartNumber(data.getManufacturePart().getNumber());
        value.setManufacturerPartName(data.getManufacturePart().getName());
        value.setType(data.getType());
        value.setConfirmationDate(data.getConfirmationDate());
        value.setValidFrom(data.getValidFrom());
        value.setContactName(data.getContact().getName());
        value.setContactEmail(data.getContact().getEmail());
        value.setContactPhone(data.getContact().getPhone());
        value.setCountry(data.getCountry());
        value.setFederalState(data.getFederalState());
        value.setOriginInformation(data.getOriginInformation());
        value.setPrefferedStatus(data.getPrefferedStatus());
        value.setTradeWorthValue(data.getTradeWorth().getValue());
        value.setTradeWorthCurrency(data.getTradeWorth().getCurrency());
        return value;
    }

    public static CustomsData toDomain(CustomsDataValue value) {
        CustomsData data = new CustomsData();
        data.setCatenaxId(value.getCatenaxId());

        Part customerPart = new Part();
        customerPart.setNumber(value.getCustomerPartNumber());
        customerPart.setName(value.getCustomerPartName());
        data.setCustomerPart(customerPart);

        Part manufacturePart = new Part();
        manufacturePart.setNumber(value.getManufacturerPartNumber());
        manufacturePart.setName(value.getManufacturerPartName());
        data.setManufacturePart(manufacturePart);

        data.setType(value.getType());
        data.setConfirmationDate(value.getConfirmationDate());
        data.setValidFrom(value.getValidFrom());

        ContactData contact = new ContactData();
        contact.setName(value.getContactName());
        contact.setEmail(value.getContactEmail());
        contact.setPhone(value.getContactPhone());
        contact.setJobTitle(value.getContactJobTitle());
        data.setContact(contact);

        data.setCountry(value.getCountry());
        data.setFederalState(value.getFederalState());
        data.setOriginInformation(value.getOriginInformation());
        data.setPrefferedStatus(value.getPrefferedStatus());

        TradeWorth tradeWorth = new TradeWorth();
        tradeWorth.setValue(value.getTradeWorthValue());
        tradeWorth.setCurrency(value.getTradeWorthCurrency());
        data.setTradeWorth(tradeWorth);

        return data;
    }
}