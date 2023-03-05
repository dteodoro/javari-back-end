package com.dteodoro.javari.security;

import java.util.UUID;

public record TokenJWTDTO(String token, Boolean userLogged, String userName, UUID userId) { }
