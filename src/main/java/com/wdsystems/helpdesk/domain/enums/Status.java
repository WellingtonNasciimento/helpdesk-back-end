package com.wdsystems.helpdesk.domain.enums;

public enum Status {

	HIGH(0, "HIGH"), 
	MEDIUM(1, "MEDIUM"),
	LOW(2, "LOW");

	private Integer code;
	private String description;
	
	private Status(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static Status toEnum(Integer code) {
		if(code == null) {
			return null;
		}
		
		for(Status x : Status.values()) {
			if(code.equals(x.getCode())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Invalid priority");
	}
}
