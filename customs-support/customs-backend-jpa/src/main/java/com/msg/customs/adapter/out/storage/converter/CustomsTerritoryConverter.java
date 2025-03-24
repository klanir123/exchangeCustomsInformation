package com.msg.customs.adapter.out.storage.converter;

import com.msg.customs.model.CustomsTerritory;
import com.msg.customs.model.CustomsTerritoryEnum;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts the {@link CustomsTerritory} to a String and vice versa.
 */
@Converter
public class CustomsTerritoryConverter implements AttributeConverter<CustomsTerritory, String> {

	@Override
	public String convertToDatabaseColumn(CustomsTerritory attribute) {
		if (attribute == null) {
			return null;
		}
		return attribute.getCode();
	}

	@Override
	public CustomsTerritory convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		return CustomsTerritoryEnum.valueOf(dbData);
	}

}
