package com.dteodoro.javari.commons.security;

import org.springframework.context.annotation.Bean;

public interface JavariSecurityConfig {
	JavariAuthClient authClient();

	JavariSecurityFilter javariSecurityFilter();

}
