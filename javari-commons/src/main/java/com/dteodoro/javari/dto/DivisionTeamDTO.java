package com.dteodoro.javari.dto;

import com.dteodoro.javari.enumeration.NFLDivision;
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
