package com.msg.customs.adapter.out.notifications.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.msg.customs.adapter.out.notifications.json.CertifiedEnumSerializer;
import com.msg.customs.model.CertifiedEnum;
import com.msg.customs.model.CustomsTerritory;
import com.msg.customs.model.CustomsTypeEnum;
import com.msg.customs.model.FederalState;
import com.msg.customs.model.RequestStatusEnum;

import lombok.Data;

/**
 * Notification data for customs.
 */
@Data
public class NotificationCustomsData {
	
	private CustomsTypeEnum type;
	
	private LocalDate confirmationDate;
	
	private LocalDate validFrom;
	
	private NotificationContactData contactInformation;
	
	private CustomsTerritory commercialCountryOfOrigin;
	
	private FederalState federalState;
	
	private List<String> originInformation;

	@JsonSerialize(using = CertifiedEnumSerializer.class)
	private CertifiedEnum preferredStatus;
	
	private NotificationTracedWorth tracedWorth;
	
	private RequestStatusEnum status; 

}
