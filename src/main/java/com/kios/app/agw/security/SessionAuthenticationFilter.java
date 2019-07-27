package com.kios.app.agw.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class SessionAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger LOG = LoggerFactory.getLogger(SessionAuthenticationFilter.class);
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		try {
			SecurityContext sc = SecurityContextHolder.getContext();
//			if(!(sc.getAuthentication() instanceof AnonymousAuthenticationToken)) { } else { }
			
		} catch (Exception e) {
			e.getMessage();
		}
		
		filterChain.doFilter(request, response);
	}
}
