package com.dteodoro.javari.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserDTO {

	private UUID id;
	private String username;
	private List<String> authorities;

}
