package com.dteodoro.javari.domain.team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.dteodoro.javari.dto.StandingDTO;
import com.dteodoro.javari.dto.TeamDTO;
import com.dteodoro.javari.dto.TeamForm;
import com.dteodoro.javari.enumeration.NFLConference;
import com.dteodoro.javari.enumeration.NFLDivision;
import com.dteodoro.javari.repository.TeamRespository;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

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

	public List<StandingDTO> findStandingByConference(NFLConference conference) {
		List<StandingDTO> standings = new ArrayList<>();
		Map<NFLDivision, List<Team>> teamsByDisivion = teamRepo.findByConference(conference).orElse(Collections.emptyList()).stream()
													   .collect(Collectors.groupingBy(Team::getDivision));
		
		for (Entry<NFLDivision, List<Team>> teamByDivision : teamsByDisivion.entrySet()) {
			standings.add(new StandingDTO(teamByDivision.getKey(), teamByDivision.getValue().stream().map(this::convertToTeamDTO).toList()));
		}
		return standings;
	}
	
	public void updateTeamScore() {
		//TODO Atualizar a pontuação dos times
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
		return modelMapper.map(team, TeamDTO.class);
	}
}
