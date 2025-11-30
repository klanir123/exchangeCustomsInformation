package com.msg.customs.application.domain.model;

import lombok.Data;

/**
 * Domain part data.
 */
@Data
public class MasterPart {
	
	private Long id;

	private String catenaxId;

	private String customerPartNumber;
	
	private String customerPartName;

	private String manufacturerPartName;

	private String manufacturerPartNumber;

	public boolean isEmpty() {
		return ((catenaxId == null) &&(customerPartNumber == null) && (customerPartName == null) && (manufacturerPartName == null) && (manufacturerPartNumber == null));
	}
}
