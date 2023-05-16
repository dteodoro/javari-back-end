package com.dteodoro.javari.commons.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
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
