package com.msg.customs.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UiCustomsReducedData {

	private Long id;

	@JsonProperty("catenaxId")
	private String catenaxId;
	
	private String customerPartNumber;
	
	private String customerPartName;

	private String preferedStatus;
	
	
}
