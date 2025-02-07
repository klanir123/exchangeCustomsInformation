package com.msg.customs.model;

public enum CertifiedEnum {
	
	N("N-NotCertified"), Y("Y-Certified");
	
	private String code;
	
	private CertifiedEnum(String code) {        
		this.code = code;
	}
        			
	public String getCode() {
		return code;
	}

}
