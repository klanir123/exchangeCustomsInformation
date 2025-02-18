package com.msg.customs.adapter.out.notifications.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msg.customs.model.CertifiedEnum;
import com.msg.customs.model.CustomsTerritory;
import com.msg.customs.model.CustomsTypeEnum;
import com.msg.customs.model.FederalState;
import com.msg.customs.model.RequestStatusEnum;

import lombok.Data;

@Data
public class NotificationCustomsData {

	@JsonProperty("catenaXId")	
	private String catenaxId;
	
	private CustomsTypeEnum type;
	
	private LocalDate confirmationDate;
	
	private LocalDate validFrom;
	
	private NotificationContactData contactInformation;
	
	private CustomsTerritory commercialCountryOfOrigin;
	
	private FederalState federalState;
	
	private List<String> originInformation;
	
	private CertifiedEnum preferredStatus;
	
	// TODO the example document uses trace worth.
	private NotificationTradeWorth tradeWorth;
	
	private RequestStatusEnum status; 

}
