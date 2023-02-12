package com.dteodoro.javari.domain.bettor;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import com.dteodoro.javari.domain.team.Team;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Bettor extends AppUser {
	
	public Bettor(UUID uuid, String username, String password, List<GrantedAuthority> authorities) {
		super(uuid, username, password, authorities);
	}
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String nickName;
	private Integer currentPosition;
	private Integer previousPosition;
	@ManyToOne(fetch = FetchType.EAGER)
	private Team favoriteTeam;
	@OneToOne(fetch = FetchType.EAGER)
	private Score score;
	
}
