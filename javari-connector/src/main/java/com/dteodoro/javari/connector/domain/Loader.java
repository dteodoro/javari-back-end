package com.dteodoro.javari.connector.domain;

import com.dteodoro.javari.connector.dto.ImportDataDTO;

public interface Loader {
	public Iterable< ? extends ImportDataDTO> load();
}
