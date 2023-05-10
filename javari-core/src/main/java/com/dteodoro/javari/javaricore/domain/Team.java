package com.dteodoro.javari.javaricore.domain;

import java.io.Serializable;
import java.util.UUID;

import com.dteodoro.javari.enumeration.NFLConference;
import com.dteodoro.javari.enumeration.NFLDivision;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Team implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private Long espnId;
	private String viewId;
	private String name;
	private String displayName;
	private String shortDisplayName;
	private String abbreviation;
	private String color;
	private String alternateColor;
	private String logo;
	@Enumerated(EnumType.STRING)
	private NFLConference conference;
	@Enumerated(EnumType.STRING)
	private NFLDivision division;
	@OneToOne
	private TeamScore score;
	
}
