package com.dteodoro.javari.domain;

import com.dteodoro.javari.dto.ImportDataDTO;

public interface Loader {
	public Iterable< ? extends ImportDataDTO> load();
}
