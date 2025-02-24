package com.msg.customs.application.domain.model;

import lombok.Data;

/**
 * Domain trade worth data.
 */
@Data
public class TradeWorth {
	
	// prefferState != N-NotCertified -> disabled, empty; else mandatory 
	private String value;
	
	// prefferState != N-NotCertified -> disabled, empty; else mandatory
	private String currency;

}
