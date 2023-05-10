package com.dteodoro.javari.javaricore.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Competition {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private int year;
}
