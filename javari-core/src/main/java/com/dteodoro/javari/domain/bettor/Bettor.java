package com.dteodoro.javari.domain.bettor;

import java.util.List;
import java.util.UUID;

import com.dteodoro.javari.domain.score.Score;
import com.dteodoro.javari.domain.user.AppUser;
import com.dteodoro.javari.domain.user.BaseUser;
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
public class Bettor extends BaseUser implements AppUser {
	
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

    public Bettor(BaseUser user) {
		this.nickName = user.getUsername();
		this.setUsername(user.getUsername());
		this.setPassword(user.getPassword());
		this.setAuthorities(user.getAuthorities());
		this.currentPosition = 0;
		this.previousPosition = 0;
    }

	@Override
	public BaseUser create(BaseUser user) {
		return new Bettor(user);
	}
}
