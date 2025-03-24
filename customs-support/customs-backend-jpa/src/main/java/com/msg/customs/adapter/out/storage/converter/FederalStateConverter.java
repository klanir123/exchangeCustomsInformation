package com.msg.customs.adapter.out.storage.converter;

import com.msg.customs.model.FederalState;
import com.msg.customs.model.GermanFederalStateEnum;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter for the {@link FederalState} enum.
 */
@Converter
public class FederalStateConverter implements AttributeConverter<FederalState, String> {

	@Override
	public String convertToDatabaseColumn(FederalState attribute) {
		if (attribute == null) {
			return null;
		}
		return attribute.getCode();
	}

	@Override
	public FederalState convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		return GermanFederalStateEnum.valueOf(dbData);
	}

}
