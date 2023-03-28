package com.wdsystems.helpdesk.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdsystems.helpdesk.domain.dtos.CredenciaisDTO;
import com.wdsystems.helpdesk.services.exceptions.InvalidCredentialException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private static final String CONTENT_TYPE = "application/json";
	
	private AuthenticationManager authenticationManager;
	private JWTUtil jwtUtil;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			CredenciaisDTO credentials = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);
			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getSenha(), new ArrayList<>());
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			
			return authentication;
		} catch (IOException e) {
			throw new InvalidCredentialException(e.getMessage());
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String username = ((UserSS) authResult.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username);
		
		response.setHeader("access-control-expose-headers", "Authorization");
		response.setHeader("Authorization", "Bearer " + token);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(CONTENT_TYPE);
		response.getWriter().append(json(HttpStatus.UNAUTHORIZED));
	}

	private CharSequence json(HttpStatus httpStatus) {
		long date = new Date().getTime();
		
		return "{"
		+ "\"timestamp\": " + date + ", " 
		+ "\"status\": " + httpStatus + ", "
		+ "\"error\": \"Invalid credentials\", "
		+ "\"message\": \"Invalid email or password\", "
		+ "\"path\": \"/login\"}";
	}
}
