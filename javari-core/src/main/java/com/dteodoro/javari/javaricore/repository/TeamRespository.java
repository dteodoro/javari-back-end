package com.dteodoro.javari.javaricore.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dteodoro.javari.enumeration.NFLDivision;
import com.dteodoro.javari.javaricore.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dteodoro.javari.enumeration.NFLConference;

public interface TeamRespository extends JpaRepository<Team, UUID>, JpaSpecificationExecutor<Team>{
	public Team findByEspnId(Long espnId);
	public Optional<List<Team>> findByConference(NFLConference conference);

	List<Team> findByConferenceAndDivision(NFLConference conference, NFLDivision division);

    public Team findByAbbreviation(String abbreviation);
}
