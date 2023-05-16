package com.dteodoro.javari.core.repository;

import com.dteodoro.javari.core.domain.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository  extends JpaRepository<BaseUser, UUID> {

	Optional<BaseUser> findByEmail(String username);
	boolean existsByEmail(String username);

}