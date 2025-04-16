package com.msg.customs.application.domain.model;

import lombok.Data;

/**
 * Domain part data.
 */
@Data
public class MasterPart {
	
	private Long id;

	private String catenaXId;

	private String customerPartNumber;
	
	private String customerPartName;

	private String manufacturerPartName;

	private String manufacturerPartNumber;

	public boolean isEmpty() {
		return ((catenaXId == null) &&(customerPartNumber == null) && (customerPartName == null) && (manufacturerPartName == null) && (manufacturerPartNumber == null));
	}
}
