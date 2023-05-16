package com.dteodoro.javari.commons.security;

import jakarta.servlet.Filter;
import org.springframework.web.filter.OncePerRequestFilter;

public interface JavariSecurityFilter extends Filter {

	JavariAuthClient getAuthClient();

}
