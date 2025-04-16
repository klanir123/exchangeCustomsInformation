package com.msg.customs.domain;

import com.msg.customs.model.CustomsTypeEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class UiMasterPart {
	
	private Long id;

	private String catenaxId;

	private String customerPartNumber;

	private String customerPartName;
	
	private String manufacturerPartNumber;
	
	private String manufacturerPartName;

}
