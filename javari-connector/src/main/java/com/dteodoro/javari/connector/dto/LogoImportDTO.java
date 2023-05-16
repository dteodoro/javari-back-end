package com.dteodoro.javari.connector.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogoImportDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
    private String href;
    
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}		
    
    
}
