package com.dteodoro.javari.commons.dto;

import com.dteodoro.javari.commons.enumeration.NFLConference;
import lombok.Getter;

import java.util.List;
@Getter
public class ConferenceTeamsDTO {
    NFLConference name;

    List<DivisionTeamDTO> divisions;

    public ConferenceTeamsDTO(NFLConference conference,List<DivisionTeamDTO> divisions){
        this.name = conference;
        this.divisions = divisions;
    }


}
