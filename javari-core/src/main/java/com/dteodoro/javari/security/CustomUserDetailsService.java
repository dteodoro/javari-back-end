package com.dteodoro.javari.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.dteodoro.javari.domain.bettor.AppUser;
import com.dteodoro.javari.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	private Map<String, AppUser> roles = new HashMap<>();
	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		return userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("username not found"));
//	}

    @PostConstruct
    public void init() {
        roles.put("admin", new AppUser(UUID.fromString("8ad2dea2-8bb0-11ed-a1eb-0242ac120002"),"admin", "admin1", getAuthority("ROLE_ADMIN")));
        roles.put("user", new AppUser(UUID.fromString("8ff672cc-8bb0-11ed-a1eb-0242ac120002"),"user", "user1", getAuthority("ROLE_USER")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return roles.get(username);
    }

    private List<GrantedAuthority> getAuthority(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
