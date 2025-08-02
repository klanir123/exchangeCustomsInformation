package com.msg.customs.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UiTask {
	
	private Long id;

	private String catenaxID;

    private String customerPartNumber;

	private String customerPartName;
	
	private String manufacturerPartNumber;
	
	private String manufacturerPartName;

	private Date requestDate;

}
