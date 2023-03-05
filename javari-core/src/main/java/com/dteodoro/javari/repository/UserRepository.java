package com.dteodoro.javari.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dteodoro.javari.domain.user.BaseUser;

public interface UserRepository extends JpaRepository<BaseUser, UUID> {
	
	Optional<BaseUser> findByUsername(String username);
	boolean existsByUsername(String username);

}
