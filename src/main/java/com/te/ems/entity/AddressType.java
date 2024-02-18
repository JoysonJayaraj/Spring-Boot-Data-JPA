package com.te.ems.entity;

public enum AddressType {
	PERMANENT("permanent"),TEMPORARY("temporary");
	
	String type;

	private AddressType(String type) {
		this.type = type;
	}
}
