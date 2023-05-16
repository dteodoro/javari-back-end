package com.dteodoro.javari.connector.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusImportDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private TypeImportDTO type;

	public TypeImportDTO getType() {
		return type;
	}

	public void setType(TypeImportDTO type) {
		this.type = type;
	}
	
}
