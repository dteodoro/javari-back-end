package com.dteodoro.javari.service;

import java.util.*;

import com.dteodoro.javari.domain.bettor.Bettor;
import com.dteodoro.javari.domain.bettor.BettorService;
import com.dteodoro.javari.domain.score.ScoreService;
import com.dteodoro.javari.domain.user.AppUser;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dteodoro.javari.domain.user.BaseUser;
import com.dteodoro.javari.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final ScoreService scoreService;
	private final BettorService bettorService;
	private final PasswordEncoder passwordEncoder;
	private Map<String, BaseUser> roles = new HashMap<>();
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("username not found"));
	}

    public List<GrantedAuthority> getAuthority(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public AppUser createUser(String username, String password) {
		if(userRepository.existsByUsername(username)){
			throw new IllegalArgumentException(String.format("the username: %s aready exists",username));
		};
		//TODO implements methods for other kind of User
		BaseUser user = new Bettor();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setAuthorities(getAuthority("ROLE_USER"));
		return bettorService.create(user);
	}

}
