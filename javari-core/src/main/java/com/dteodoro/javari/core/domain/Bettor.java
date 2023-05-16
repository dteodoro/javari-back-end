package com.dteodoro.javari.core.domain;

import java.io.Serial;

import com.dteodoro.javari.commons.enumeration.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Bettor extends BaseUser implements AppUser {

	@Serial
	private static final long serialVersionUID = 1L;

	private String nickName;
	private Integer currentPosition;
	private Integer previousPosition;
	@ManyToOne(fetch = FetchType.EAGER)
	private Team favoriteTeam;

	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Score score;

	public Bettor(BaseUser user) {
		this.nickName = user.getFirstname();
		this.setFirstname(user.getFirstname());
		this.setLastname(user.getLastname());
		this.setEmail(user.getUsername());
		this.setPassword(user.getPassword());
		this.setRole(user.getRole());
		this.currentPosition = 0;
		this.previousPosition = 0;
		this.setRole(Role.USER);
	}

	@Override
	public BaseUser create(BaseUser user) {
		return new Bettor(user);
	}
}
