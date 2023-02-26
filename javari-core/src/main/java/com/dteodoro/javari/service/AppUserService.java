package com.dteodoro.javari.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dteodoro.javari.domain.bettor.Bettor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dteodoro.javari.domain.bettor.AppUser;
import com.dteodoro.javari.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

	private final UserRepository userRepository;
	private Map<String, AppUser> roles = new HashMap<>();
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("username not found"));
	}

    public List<GrantedAuthority> getAuthority(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

	public void save(Bettor bettor) {
		userRepository.save(bettor);
	}

	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
}
