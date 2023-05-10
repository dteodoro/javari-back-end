package com.dteodoro.javari.javaricore.repository;

import com.dteodoro.javari.javaricore.domain.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository  extends JpaRepository<BaseUser, UUID> {

	Optional<BaseUser> findByEmail(String username);
	boolean existsByEmail(String username);

}