package ar.edu.unnoba.poo2025.torneos.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DocType {
	DNI("DNI"),
	PASSPORT("PASSPORT"),
	OTHER("OTHER");

	private final String value;

	DocType(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

	@JsonCreator
	public static DocType fromValue(String text) {
		for (DocType type : DocType.values()) {
			if (type.value.equalsIgnoreCase(text)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid DocType: " + text);
	}
}
