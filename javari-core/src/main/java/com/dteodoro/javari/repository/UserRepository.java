package com.dteodoro.javari.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dteodoro.javari.domain.bettor.AppUser;

public interface UserRepository extends JpaRepository<AppUser, UUID> {
	
	Optional<AppUser> findByUsername(String username);

}
