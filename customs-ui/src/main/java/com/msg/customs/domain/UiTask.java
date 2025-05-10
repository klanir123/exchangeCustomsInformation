package com.msg.customs.domain;

import lombok.Data;

@Data
public class UiTask {
	
	private Long id;

	private String customerPartNumber;

	private String customerPartName;
	
	private String manufacturerPartNumber;
	
	private String manufacturerPartName;

}
