package com.msg.customs.adapter.in.web.model;

import lombok.Data;

import java.util.Date;

@Data
public class TaskDto {
	
	private Long id;

	private String catenaXId;

	private String customerPartNumber;

	private String customerPartName;
	
	private String manufacturerPartNumber;
	
	private String manufacturerPartName;

	private Date requestDate;

}
