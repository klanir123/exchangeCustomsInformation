package com.msg.customs.application.domain.model;

import lombok.Data;

import java.util.Date;

/**
 * Domain task data.
 */
@Data
public class Task {
	
	private Long id;

	private String catenaXId;

	private String customerPartNumber;
	
	private String customerPartName;

	private String manufacturerPartName;

	private String manufacturerPartNumber;

	private Date requestDate;

	public boolean isEmpty() {
		return ((catenaXId == null) &&(customerPartNumber == null) && (customerPartName == null) && (manufacturerPartName == null) && (manufacturerPartNumber == null));
	}
}
