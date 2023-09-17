package com.dteodoro.javari.game.service;

import com.dteodoro.javari.commons.dto.BettorDTO;
import com.dteodoro.javari.commons.dto.TeamDTO;
import com.dteodoro.javari.core.domain.Bettor;
import com.dteodoro.javari.core.domain.Team;
import com.dteodoro.javari.core.repository.BettorRepository;
import com.dteodoro.javari.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BettorService {

	private final ModelMapper modelMapper;
	private final BettorRepository bettorRepo;
	private final UserRepository userRepository;

    private final TeamService teamService;

	private final ScoreService scoreService;

	public BettorDTO findBettorDetails(UUID bettorId) {
		return modelMapper.map(bettorRepo.findById(bettorId).orElse(null), BettorDTO.class);

	}

    public Bettor findById(UUID bettorId) {
		return bettorRepo.findById(bettorId).orElse(null);
    }


	public List<BettorDTO> findRivals(UUID bettorId) {
		List<Bettor> bettors = bettorRepo.findAllByOrderByCurrentPositionAsc();
		return bettors.stream().map(b -> modelMapper.map(b, BettorDTO.class)).toList();
	}

	public void save(Bettor bettor) {
		bettorRepo.save(bettor);
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

	public boolean hasPermission(UUID bettorId, String roleName){
		Bettor bettor = findById(bettorId);
		if(bettor != null){
			return ("ROLE_"+bettor.getRole().toString()).equals(roleName);
		}
		return false;
	}

	public void updatePosition() {
		final List<Bettor> bettorsByScore = bettorRepo.findAllByOrderByScorePointsDesc();
		if (!bettorsByScore.isEmpty()) {

			for (int i = 0; i < bettorsByScore.size(); i++) {
				var firstPosition = i == 0;
				var currentBettor = bettorsByScore.get(i);

				currentBettor.setPreviousPosition(currentBettor.getCurrentPosition());

				if(firstPosition){
					currentBettor.setCurrentPosition(i + 1);
				} else {
					var previousBettor = bettorsByScore.get(i-1);
					if (currentBettor.getScore().getPoints().equals(previousBettor.getScore().getPoints())) {
						currentBettor.setCurrentPosition(previousBettor.getCurrentPosition());
					} else {
						currentBettor.setCurrentPosition(previousBettor.getCurrentPosition() + 1);
					}
				}
				bettorRepo.save(currentBettor);
			}
		}
	}

    public void updateImage(final String imagePath, final UUID bettorId) {
		if(StringUtils.hasText(imagePath)){
			var bettor = bettorRepo.findById(bettorId).orElse(null);
			if(bettor != null) {
				bettor.setImage(imagePath);
				bettorRepo.save(bettor);
			}
		}
    }
}
