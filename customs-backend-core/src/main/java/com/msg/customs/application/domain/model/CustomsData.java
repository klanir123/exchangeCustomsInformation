package com.msg.customs.application.domain.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.msg.customs.model.CertifiedEnum;
import com.msg.customs.model.CustomsTerritory;
import com.msg.customs.model.CustomsTypeEnum;
import com.msg.customs.model.FederalState;

import lombok.Data;

@Data
public class CustomsData {
	
	// internal id to store the entity
	private Long id;
	
	private String catenaxId;
	
	private Part customerPart;
	
	private Part manufacturePart;
	
	// required
	private CustomsTypeEnum type;
	
	// internal date to store the date of the creation 
	private LocalDate confirmationDate;
	
	// required
	private LocalDate validFrom;
	
	// required
	private ContactData contact;
	
	// required
	private CustomsTerritory country;
	
	// country != DE -> disabled, = 99
	private FederalState federalState;
	
	// country == EU -> multi selection possible
	private List<String> originInformation;
	
	// country != EU -> disabled, = N-NotCertified
	private CertifiedEnum prefferedStatus;
	
	private TradeWorth tradeWorth;
}
