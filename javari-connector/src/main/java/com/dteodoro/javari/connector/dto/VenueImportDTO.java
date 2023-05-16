package com.dteodoro.javari.connector.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VenueImportDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	String id;
	String fullName;
	String city;
	String state;
	Integer capacity;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	@Override
	public String toString() {
		return "VenueDTO [id=" + id + ", fullName=" + fullName + ", city=" + city + ", state=" + state + ", capacity="
				+ capacity + "]";
	}
	
	
	
}
