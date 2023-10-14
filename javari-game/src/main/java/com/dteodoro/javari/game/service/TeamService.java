package com.dteodoro.javari.game.service;

import com.dteodoro.javari.commons.dto.*;
import com.dteodoro.javari.core.domain.*;
import com.dteodoro.javari.commons.enumeration.NFLConference;
import com.dteodoro.javari.commons.enumeration.NFLDivision;
import com.dteodoro.javari.core.repository.TeamRespository;
import com.dteodoro.javari.core.repository.TeamScoreRepository;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TeamService {

	private final TeamRespository teamRepo;
	private final ModelMapper modelMapper;
	private final TeamScoreRepository teamScoreRepo;

	public void saveTeam(Team team) {
		Team currentTeam = teamRepo.findByEspnId(team.getEspnId());
		if(currentTeam != null) {
			team.setId(currentTeam.getId());
		}
		teamRepo.save(team);
	}

	public Team findByEspnId(Long espnId) {
		return teamRepo.findByEspnId(espnId);
	}

	public Page<TeamDTO> findAll(TeamForm teamForm, Pageable pageable) {
		return teamRepo.findAll(getTeamSpec(teamForm), pageable).map(this::convertToTeamDTO);
	}

	public TeamDTO findById(UUID teamId) {
		return convertToTeamDTO(teamRepo.findById(teamId).orElse(null));
	}

	public Team findTeamById(UUID teamId){
		return teamRepo.findById(teamId).orElse(null);
	}

	public List<StandingDTO> findStandingByConference(NFLConference conference) {
		List<StandingDTO> standings = new ArrayList<>();
		Map<NFLDivision, List<Team>> teamsByDisivion = teamRepo.findByConference(conference).orElse(Collections.emptyList()).stream()
													   .collect(Collectors.groupingBy(Team::getDivision));
		
		for (Entry<NFLDivision, List<Team>> teamByDivision : teamsByDisivion.entrySet()) {
			standings.add(new StandingDTO(teamByDivision.getKey(), teamByDivision.getValue().stream().map(this::convertToTeamDTO).toList()));
		}
		return standings;
	}

	private Specification<Team> getTeamSpec(TeamForm teamForm) {
		return (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (teamForm.getConference() != null) {
				Path<NFLConference> conferencePath = root.<NFLConference>get("conference");
				Predicate conferencePredicate = builder.equal(conferencePath, teamForm.getConference());
				predicates.add(conferencePredicate);
			}
			if (teamForm.getDivision() != null) {
				Path<NFLDivision> divisionPath = root.<NFLDivision>get("division");
				Predicate divisionPredicate = builder.equal(divisionPath, teamForm.getDivision());
				predicates.add(divisionPredicate);
			}
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
	private TeamDTO convertToTeamDTO(Team team) {
		TeamDTO teamDTO = modelMapper.map(team, TeamDTO.class);
		if(!StringUtils.hasText(teamDTO.getScoreScoreSummary())){
			teamDTO.setScoreScoreSummary("0-0-0");
		}
		return teamDTO;
	}

	public List<ConferenceTeamsDTO> findByTypes(NFLConference conferenceType, NFLDivision divisionType)  {
		List<ConferenceTeamsDTO> conferences = createConferencesList(conferenceType,divisionType);
		for (ConferenceTeamsDTO conf : conferences) {
			for(DivisionTeamDTO div : conf.getDivisions()){
				div.setTeams(
						teamRepo.findByConferenceAndDivision(
									conferenceType == null ? conf.getName() : conferenceType,
									divisionType == null ? div.getName(): divisionType)
										.stream().map(this::convertToTeamDTO).toList());
			}
		}
		return conferences;
	}

	private List<ConferenceTeamsDTO> createConferencesList(NFLConference conferenceType, NFLDivision divisionType){
		if(conferenceType != null){
			return List.of(new ConferenceTeamsDTO(conferenceType,createDivisionList(divisionType)));
		}
		return List.of(
				new ConferenceTeamsDTO(NFLConference.NFC,createDivisionList(divisionType)),
				new ConferenceTeamsDTO(NFLConference.AFC,createDivisionList(divisionType)));
	}
	private List<DivisionTeamDTO> createDivisionList(NFLDivision divisionType){
		if(divisionType != null){
			return List.of(new DivisionTeamDTO(divisionType));
		}
		return List.of(
				new DivisionTeamDTO(NFLDivision.EAST),
				new DivisionTeamDTO(NFLDivision.NORTH),
				new DivisionTeamDTO(NFLDivision.SOUTH),
				new DivisionTeamDTO(NFLDivision.WEST));
	}

	public void saveTeam(TeamDTO teamDTO) {
		if(teamDTO != null){
			saveTeam(modelMapper.map(teamDTO, Team.class));
		}
	}

    public void saveTeamScore(final TeamScoreDTO teamScoreDTO) {
		Team team = teamRepo.findByEspnId(teamScoreDTO.getTeamId());
		TeamScore score;
		if(team.getScore() != null){
			score = team.getScore();
		}else{
			score = new TeamScore();
		}
		score.setWins(teamScoreDTO.getWins());
		score.setLosses(teamScoreDTO.getLosses());
		score.setTies(teamScoreDTO.getTies());
		score.setWinPercentage(calcWinPercent(teamScoreDTO.getWinPercent()));
		score.setHome(teamScoreDTO.getHome());
		score.setRoad(teamScoreDTO.getRoad());
		score.setVersusDiv(teamScoreDTO.getVersusDiv());
		score.setVersusConf(teamScoreDTO.getVersusConf());
		score.setPointsFor(teamScoreDTO.getPointsFor());
		score.setPointsAgainst(teamScoreDTO.getPointsAgainst());
		score.setPointDifferential(teamScoreDTO.getPointDifferential());
		score.setStreak(teamScoreDTO.getStreak());
		score.setSeasonName(teamScoreDTO.getSeasonName());
		score.setSeasonYear(teamScoreDTO.getSeasonYear());
		score.updateScoreSummary();
		teamScoreRepo.save(score);
		team.setScore(score);
		teamRepo.save(team);
    }

	private double calcWinPercent(String winPercent) {
		if(winPercent == null) return 0;
		Double percent = Double.valueOf(winPercent.startsWith(".") ? "0"+winPercent : winPercent);
		return percent * 100;
	}
}
