package com.msg.customs.model;

import java.util.List;

public interface CustomsTerritory {
	
	String getCode();
	
	default boolean belongsToCustomsUnion(CustomsUnion customsUnion) {
		if (getCustomsUnion() == null) {
			return false;
		}
		return getCustomsUnion().equals(customsUnion);
	}
	
	CustomsUnion getCustomsUnion();
	
	default boolean hasFederatedStates() {
		return getFederatedStates() != null && !getFederatedStates().isEmpty();
	}
	
	List<FederatedState> getFederatedStates();
	
}
