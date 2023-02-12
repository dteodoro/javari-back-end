package com.dteodoro.javari.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import com.dteodoro.javari.domain.bettor.Bettor;

@Repository
public class MockUserRepository {

	public Optional<Bettor> findByUsername(String username){
		return Optional.of(new Bettor(UUID.fromString("8ad2dea2-8bb0-11ed-a1eb-0242ac120002"),"admin", "admin1", getAuthority("ROLE_ADMIN")));
	}

	private List<GrantedAuthority> getAuthority(String role) {
		return Collections.singletonList(new SimpleGrantedAuthority(role));
	}
}
