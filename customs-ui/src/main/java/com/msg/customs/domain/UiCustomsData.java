package com.msg.customs.domain;

import lombok.Data;

@Data
public class UiCustomsData {
	
	private String catenaxId;

	private String customerPartNumber;

	private String customerPartName;
	
	private String manufacturerPartNumber;
	
	private String manufacturerPartName;
	
	private String type;
	
	private String validFrom;
	
	private String contactName;
	
	private String contactPhone;
	
	private String contactEmail;
	
	private String contactJobTitle;
	
	private String country;
	
	private String federalState;
	
	// Must be a list of 
	private String originInformation;
	
	private String prefferedStatus;
	
	private String tradeWorthValue;
	
	private String tradeWorthCurrency;
	

}
