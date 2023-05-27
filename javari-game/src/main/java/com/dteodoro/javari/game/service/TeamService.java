package com.dteodoro.javari.game.service;

import com.dteodoro.javari.commons.enumeration.ScheduleStatus;
import com.dteodoro.javari.core.domain.*;
import com.dteodoro.javari.commons.dto.ConferenceTeamsDTO;
import com.dteodoro.javari.commons.dto.DivisionTeamDTO;
import com.dteodoro.javari.commons.dto.StandingDTO;
import com.dteodoro.javari.commons.dto.TeamDTO;
import com.dteodoro.javari.commons.enumeration.NFLConference;
import com.dteodoro.javari.commons.enumeration.NFLDivision;
import com.dteodoro.javari.core.repository.TeamRespository;
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
	
	public void updateTeamScore(Schedule currentSchedule) {
		if(currentSchedule.getStatus().equals(ScheduleStatus.STATUS_FINAL)){
			Competitor homeCompetitor = currentSchedule.getHomeCompetitor();
			Competitor awayCompetitor = currentSchedule.getAwayCompetitor();
			boolean tie = homeCompetitor.getWinner().equals(awayCompetitor.getWinner());
			boolean sameConference = homeCompetitor.getTeam().getConference().equals(awayCompetitor.getTeam().getConference());
			updateScore(homeCompetitor.getTeam(),sameConference,homeCompetitor.getWinner(), tie);
			updateScore(awayCompetitor.getTeam(),sameConference,homeCompetitor.getWinner(), tie);
		}
	}

	private void updateScore(Team team,boolean sameConference, boolean winner, boolean tie) {
		if(team.getScore() == null){
			team.setScore(new TeamScore());
		}
		TeamScore score  = team.getScore();
		score.setWins(winner ? score.getWins() + 1 : score.getWins());
		score.setLosses(winner ? score.getLosses() : score.getLosses() + 1);
		score.setTies(tie ? score.getTies() + 1 : score.getTies());
		score.setWinsOnConference(sameConference ? score.getWinsOnConference() + 1 : score.getWinsOnConference());
		score.updateScoreSummary();
		score.updateWinPercentage();
		teamRepo.save(team);
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
}
