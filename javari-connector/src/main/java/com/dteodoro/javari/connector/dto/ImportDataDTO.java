package com.dteodoro.javari.connector.dto;

import com.dteodoro.javari.commons.dto.DomainDTO;

import java.io.Serializable;

public interface ImportDataDTO extends Serializable {
    public DomainDTO toDomainDto();
}
