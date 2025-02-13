package com.msg.customs.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.msg.customs.model.CustomsTypeEnum;

import lombok.Data;

@Data
public class UiCustomsData {
	
	private Long id;
	
	private boolean completed;
	
	private String catenaxId;

	private String customerPartNumber;

	private String customerPartName;
	
	private String manufacturerPartNumber;
	
	private String manufacturerPartName;
	
	private CustomsTypeEnum type;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate validFrom;
	
	private String contactName;
	
	private String contactPhone;
	
	private String contactEmail;
	
	private String contactJobTitle;
	
	private String country;
	
	private String federalState;
	
	// Must be a list of 
	private List<String> originInformations;
	
	private String prefferedStatus;
	
	private String tradeWorthValue;
	
	private String tradeWorthCurrency;
	

}
