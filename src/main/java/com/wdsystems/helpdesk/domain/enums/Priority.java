package com.wdsystems.helpdesk.domain.enums;

public enum Priority {

	OPEN(0, "OPEN"), 
	IN_PROGRESS(1, "IN_PROGRESS"),
	CLOSED(2, "CLOSED");

	private Integer code;
	private String description;
	
	private Priority(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static Priority toEnum(Integer code) {
		if(code == null) {
			return null;
		}
		
		for(Priority x : Priority.values()) {
			if(code.equals(x.getCode())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Invalid status");
	}
}
