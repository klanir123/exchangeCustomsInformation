package com.msg.customs.application.domain.model;

import java.util.Date;
import java.util.List;

import com.msg.customs.model.CustomsTypeEnum;

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
	private Date confirmationDate;
	
	// required
	private Date validFrom;
	
	// required
	private ContactData contact;
	
	// required
	private String country;
	
	// country != DE -> disabled, = 99
	private String federalState;
	
	// country == EU -> multi selection possible
	private List<String> originInformation;
	
	// country != EU -> disabled, = N-NotCertified
	private String prefferedStatus;
	
	private TradeWorth tradeWorth;
}
