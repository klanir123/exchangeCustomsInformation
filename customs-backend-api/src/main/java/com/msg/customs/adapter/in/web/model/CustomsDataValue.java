package com.msg.customs.adapter.in.web.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class CustomsDataValue {

    private String catenaxId;

    private String customerPartNumber;
    private String customerPartName;

    private String manufacturerPartNumber;
    private String manufacturerPartName;

    private CustomsTypeEnum type;

    private Date confirmationDate;

    private Date validFrom;

    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String contactJobTitle;

    private String country;

    private String federalState;

    private List<String> originInformation;

    private String prefferedStatus;

    private String tradeWorthValue;
    private String tradeWorthCurrency;
}
