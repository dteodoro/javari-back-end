package com.dteodoro.javari.dto;

import com.dteodoro.javari.enumeration.NFLConference;
import lombok.Data;
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
