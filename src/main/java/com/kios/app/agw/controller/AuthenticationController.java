package com.kios.app.agw.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kios.app.agw.entities.User;
import com.kios.app.agw.repository.UserRepository;
import com.kios.app.agw.security.UserPrincipal;
import com.kios.app.agw.service.AuthenticationService;
import com.kios.app.agw.util.Response;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	AuthenticationService authenticationService;
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);
	@GetMapping("/hello")
	public String getAuthHello() {
		return "Hello from the auth gateway.";
	}
	
	@PostMapping("/register")
	public @ResponseBody ResponseEntity<User> registerNewUser(@RequestBody User user) {
		User u = authenticationService.registerUser(user);
		if(u!=null) {
			return new Response<>(u, HttpStatus.ACCEPTED);
		} else {
			return new Response<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/login")
	public @ResponseBody Response<Map<String, String>> loginUser(@RequestBody User user) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
					user.getUsername(),
					user.getPassword())
			);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		Map<String, String> responseMap = new HashMap<>();
		if(authentication.getPrincipal()!=null) {
			String username  = ((UserPrincipal) authentication.getPrincipal()).getUsername();
			String firstName = ((UserPrincipal) authentication.getPrincipal()).getFirstName();
			String lastName = ((UserPrincipal) authentication.getPrincipal()).getLastName();
			responseMap.put("username", username);
			responseMap.put("firstName", firstName);
			responseMap.put("lastName", lastName);
			return new Response<>(responseMap, HttpStatus.OK);
		} else {
			responseMap.put("Error", "Error with request");
			return new Response<>(responseMap, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/currentUser")
	public @ResponseBody Response<Map<String, String>> getCurrentUser() {
		Map<String, String> responseMap = new HashMap<>();
		if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
			responseMap.put("Error", "Error with request");
			return new Response<>(responseMap, HttpStatus.BAD_REQUEST);
		} else {
			UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username  = user.getUsername();
			String firstName = user.getFirstName();
			String lastName = user.getLastName();
			responseMap.put("username", username);
			responseMap.put("firstName", firstName);
			responseMap.put("lastName", lastName);
			return new Response<>(responseMap, HttpStatus.OK);
			
		}
	}
}
