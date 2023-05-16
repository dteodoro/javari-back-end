package com.dteodoro.javari.auth.service;

import com.dteodoro.javari.auth.dto.AuthenticationRequest;
import com.dteodoro.javari.auth.dto.AuthenticationResponse;
import com.dteodoro.javari.auth.dto.RegisterRequest;
import com.dteodoro.javari.core.domain.*;
import com.dteodoro.javari.core.repository.BettorRepository;
import com.dteodoro.javari.core.repository.ScoreRepository;
import com.dteodoro.javari.core.repository.TokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final BettorRepository bettorRepo;
	private final ScoreRepository scoreRepo;
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {
		var user = BaseUser.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.build();

		Bettor bettor = bettorRepo.save(new Bettor(user));
		Score currentScore = scoreRepo.findByBettorId(bettor.getId()).orElse(null);
		if(currentScore==null) {
			currentScore = scoreRepo.save(new Score(bettor));
		}
		bettor.setScore(currentScore);
		var savedUser =  bettorRepo.save(bettor);

		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		saveUserToken(savedUser, jwtToken);
		return AuthenticationResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
				)
		);
		var user = bettorRepo.findByEmail(request.getEmail())
				.orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, jwtToken);
		return AuthenticationResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.build();
	}

	private void saveUserToken(BaseUser user, String jwtToken) {
		var token = Token.builder()
				.user(user)
				.token(jwtToken)
				.tokenType(TokenType.BEARER)
				.expired(false)
				.revoked(false)
				.build();
		tokenRepository.save(token);
	}

	private void revokeAllUserTokens(BaseUser user) {
		var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);
	}

	public void refreshToken(
			HttpServletRequest request,
			HttpServletResponse response
	) throws IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String userEmail;
		if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
			return;
		}
		refreshToken = authHeader.substring(7);
		userEmail = jwtService.extractUsername(refreshToken);
		if (userEmail != null) {
			var user = this.bettorRepo.findByEmail(userEmail)
					.orElseThrow();
			if (jwtService.isTokenValid(refreshToken, user)) {
				var accessToken = jwtService.generateToken(user);
				revokeAllUserTokens(user);
				saveUserToken(user, accessToken);
				var authResponse = AuthenticationResponse.builder()
						.accessToken(accessToken)
						.refreshToken(refreshToken)
						.build();
				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
	}

	public BaseUser validateToken(String authHeader) throws IOException {
			final String refreshToken;
			final String userEmail;
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				return null;
			}
			refreshToken = authHeader.substring(7);
			userEmail = jwtService.extractUsername(refreshToken);
			if (userEmail != null) {
				var user = this.bettorRepo.findByEmail(userEmail)
						.orElseThrow();
				if (jwtService.isTokenValid(refreshToken, user)) {
					return user;
				}
			}
			return null;
	}
}