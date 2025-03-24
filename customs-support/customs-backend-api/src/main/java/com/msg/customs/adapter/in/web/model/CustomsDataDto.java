package com.msg.customs.adapter.in.web.model;

import java.time.LocalDate;
import java.util.List;

import com.msg.customs.model.CustomsTypeEnum;
import com.msg.customs.model.RequestStatusEnum;

import lombok.Data;

/**
 * Simple class to exchange customs data with ui.
 */
@Data
public class CustomsDataDto {
	
	private Long id;
	
    private String catenaxId;

    private RequestStatusEnum requestStatus;
    
    private String customerPartNumber;
    private String customerPartName;

    private String manufacturerPartNumber;
    private String manufacturerPartName;

    private CustomsTypeEnum type;

    private LocalDate confirmationDate;

    private LocalDate validFrom;

    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String contactJobTitle;

    private String country;

    private String federalState;

    private List<String> originInformation;

    private String prefferedStatus;

    private String tracedWorthValue;
    private String tracedWorthCurrency;
}
