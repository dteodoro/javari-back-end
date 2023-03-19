package com.dteodoro.javari.domain.bettor;

import java.util.List;
import java.util.UUID;

import com.dteodoro.javari.domain.score.ScoreService;
import com.dteodoro.javari.domain.team.Team;
import com.dteodoro.javari.domain.team.TeamService;
import com.dteodoro.javari.domain.user.AppUser;
import com.dteodoro.javari.domain.user.BaseUser;
import com.dteodoro.javari.dto.BettorDTO;
import com.dteodoro.javari.dto.TeamDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dteodoro.javari.repository.BettorRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BettorService {

	private final ModelMapper modelMapper;
	private final BettorRepository bettorRepo;

    private final TeamService teamService;

	private final ScoreService scoreService;

	public BettorDTO findBettorDetails(UUID bettorId) {
		return modelMapper.map(bettorRepo.findById(bettorId).orElse(null), BettorDTO.class);

	}

    public Bettor findById(UUID bettorId) {
		return bettorRepo.findById(bettorId).orElse(null);
    }

	public boolean hasPermission(UUID bettorId, String roleName){
		Bettor bettor = findById(bettorId);
		if(bettor != null){
			List<String> roles = bettor.getAuthorities().stream().map(ga -> ga.getAuthority()).toList();
			return roles.contains(roleName);
		}
		return false;
	}

	public List<BettorDTO> findRivals(UUID bettorId) {
		List<Bettor> bettors = bettorRepo.findAll();
		return bettors.stream().map(b->modelMapper.map(b,BettorDTO.class)).toList();
	}

	public void save(Bettor bettor) {
		bettorRepo.save(bettor);
	}

	public AppUser create(BaseUser user) {
		Bettor bettor = bettorRepo.save(new Bettor(user));
		bettor.setScore(scoreService.createScore(bettor));
		bettorRepo.save(bettor);
		return bettor;
	}

    public TeamDTO setFavoriteTeam(UUID bettorId, UUID teamId) {
		Bettor bettor = bettorRepo.findById(bettorId).orElse(null);
		Team team = teamId != null ? teamService.findTeamById(teamId) : null;
		bettor.setFavoriteTeam(team);
		bettorRepo.save(bettor).getFavoriteTeam();
		return team != null ? modelMapper.map(team, TeamDTO.class) : null;
    }

	public TeamDTO findFavoriteTeam(UUID bettorId) {
		Bettor bettor = bettorRepo.findById(bettorId).orElse(null);
		if(bettor != null ){
			return modelMapper.map(bettor.getFavoriteTeam(), TeamDTO.class);
		}
		return null;
	}
}
