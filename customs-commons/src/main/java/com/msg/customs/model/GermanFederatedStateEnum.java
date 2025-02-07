package com.msg.customs.model;

public enum GermanFederatedStateEnum implements FederatedState {

	BW("Baden-Württemberg"), 
	BY("Bayern"), 
	BE("Berlin"), 
	BB("Brandenburg"), 
	HB("Bremen"), 
	HH("Hamburg"), 
	HE("Hessen"),
	MV("Mecklenburg-Vorpommern"), 
	NI("Niedersachsen"), 
	NW("Nordrhein-Westfalen"), 
	RP("Rheinland-Pfalz"), 
	SL("Saarland"),
	SN("Sachsen"), 
	ST("Sachsen-Anhalt"), 
	SH("Schleswig-Holstein"), 
	TH("Thüringen"),

	;

	private String federatedStateName;

	private GermanFederatedStateEnum(String federatedStateName) {
		this.federatedStateName = federatedStateName;
	}

	@Override
	public String getCode() {
		return name();
	}

	@Override
	public String getFederatedStateName() {
		return federatedStateName;
	}

	

}
