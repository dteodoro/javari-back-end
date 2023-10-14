package com.dteodoro.javari.commons.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum NFLStats {
    W("wins"),
    L("losses"),
    T("ties"),
    PCT("winPercent"),
    HOME("home"),
    AWAY("road"),
    DIV("vsdiv"),
    CONF("vsconf"),
    PF("pointsfor"),
    PA("pointsagainst"),
    DIFF("differential"),
    STRK("streak");

    private String description;
    NFLStats(String description){
        this.description = description;
    }
}
