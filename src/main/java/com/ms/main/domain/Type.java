package com.ms.main.domain;

import lombok.Getter;

@Getter
public enum Type {
	
	Hospital(0, "hospital"),
	Pharmacy(1, "pharmaccy");
	
	private int typeInt;
	private String typeStr;
	
	Type(int typeInt, String typeStr) {
		this.typeInt = typeInt;
		this.typeStr = typeStr;
	}
}
