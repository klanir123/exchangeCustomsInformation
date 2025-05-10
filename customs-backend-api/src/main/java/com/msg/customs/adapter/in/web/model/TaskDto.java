package com.msg.customs.adapter.in.web.model;

import lombok.Data;

@Data
public class TaskDto {
	
	private Long id;

	private String customerPartNumber;

	private String customerPartName;
	
	private String manufacturerPartNumber;
	
	private String manufacturerPartName;

}
