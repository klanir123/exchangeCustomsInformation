package com.msg.customs.model;

import java.util.List;
import java.util.Locale;

public enum CustomsTerritoryEnum implements CustomsTerritory {

	DE(Locale.GERMANY, CustomsUnionEnum.EU, List.of(GermanFederalStateEnum.values())),
	FR(Locale.FRANCE, CustomsUnionEnum.EU),
	IT(Locale.ITALY, CustomsUnionEnum.EU),
	GB(Locale.UK),
	CN(Locale.CHINA),
	US(Locale.US),
	
	;
	
	private Locale locale;
	
	private CustomsUnion customsUnion;
	
	private List<FederalState> federatedStates;
	
	private CustomsTerritoryEnum(Locale locale) {
		this(locale, null);
	}

	private CustomsTerritoryEnum(Locale locale, CustomsUnion customsUnion) {
		this(locale, customsUnion, null);
    }
	
	private CustomsTerritoryEnum(Locale locale, CustomsUnion customsUnion, List<FederalState> federatedStates) {
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
	public List<FederalState> getFederatedStates() {
		return federatedStates;
	}
	
}
