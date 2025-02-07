package com.msg.customs.model;

import java.util.List;
import java.util.Locale;

public enum CustomsTeritoryEnum implements CustomsTerritory {

	DE(Locale.GERMANY, CustomsUnionEnum.EU, List.of(GermanFederatedStateEnum.values())),
	FR(Locale.FRANCE, CustomsUnionEnum.EU),
	IT(Locale.ITALY, CustomsUnionEnum.EU),
	GB(Locale.UK),
	CN(Locale.CHINA),
	US(Locale.US),
	
	;
	
	private Locale locale;
	
	private CustomsUnion customsUnion;
	
	private List<FederatedState> federatedStates;
	
	private CustomsTeritoryEnum(Locale locale) {
		this(locale, null);
	}

	private CustomsTeritoryEnum(Locale locale, CustomsUnion customsUnion) {
		this(locale, customsUnion, null);
    }
	
	private CustomsTeritoryEnum(Locale locale, CustomsUnion customsUnion, List<FederatedState> federatedStates) {
		this.locale = locale;
		this.customsUnion = customsUnion;
		this.federatedStates = federatedStates;
	}


	@Override
	public String getCode() {
		return name();
	}

	@Override
	public CustomsUnion getCustomsUnion() {
		return customsUnion;
	}

	@Override
	public List<FederatedState> getFederatedStates() {
		return federatedStates;
	}
	
}
