package com.msg.customs.domain;

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
	
	private String catenaxId;
	
	private String customerPartNumber;
	
	private String customerPartName;
	
	
}
