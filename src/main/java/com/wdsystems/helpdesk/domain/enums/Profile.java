package com.wdsystems.helpdesk.domain.enums;

public enum Profile {

	ADMIN(0, "ROLE_ADMIN"), 
	CUSTOMER(1, "ROLE_CUSTOMER"),
	TECHNICAL(2, "ROLE_TECHNICAL");

	private Integer code;
	private String description;
	
	private Profile(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static Profile toEnum(Integer code) {
		if(code == null) {
			return null;
		}
		
		for(Profile x : Profile.values()) {
			if(code.equals(x.getCode())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Invalid profile");
	}
}
