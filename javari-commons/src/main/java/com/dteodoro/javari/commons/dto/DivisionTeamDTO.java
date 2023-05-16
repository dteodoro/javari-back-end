package com.dteodoro.javari.commons.dto;

import com.dteodoro.javari.commons.enumeration.NFLDivision;
import lombok.Data;

import java.util.List;
@Data
public class DivisionTeamDTO {

    NFLDivision name;
    List<TeamDTO> teams;

    public DivisionTeamDTO(NFLDivision division){
        this.name = division;
    }
}
