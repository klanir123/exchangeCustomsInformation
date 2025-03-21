package com.msg.customs.application.domain.model;

import lombok.Data;

/**
 * Domain part data.
 */
@Data
public class Part {
	
	private String number;
	
	private String name;

	public boolean isEmpty() {
		return number == null && name == null;
	}
}
