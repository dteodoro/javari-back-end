package com.dteodoro.javari.dto;

import java.util.UUID;
public record TokenJWTDTO(String token, Boolean userLogged, String userName, UUID userId) { }