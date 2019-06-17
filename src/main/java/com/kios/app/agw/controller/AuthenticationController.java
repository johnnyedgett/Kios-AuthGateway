package com.kios.app.agw.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
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
	Environment env;
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);
	@GetMapping("/hello")
	public String getAuthHello() {
		return "Hello from the auth gateway.";
	}
	
	@GetMapping("/env")
	public Map<String, Object> getEnvironmentVariables(){
		Map<String, Object> map = new HashMap();
        for(Iterator it = ((AbstractEnvironment) env).getPropertySources().iterator(); it.hasNext(); ) {
            PropertySource propertySource = (PropertySource) it.next();
            if (propertySource instanceof MapPropertySource) {
                map.putAll(((MapPropertySource) propertySource).getSource());
            }
        }
		return map;
	}
	
	@PostMapping("/register")
	public @ResponseBody ResponseEntity<User> registerNewUser(@RequestBody User user) {
		User toSave = new User();
		toSave.setEmail(user.getEmail());
		toSave.setFirstName(user.getFirstName());
		toSave.setLastName(user.getLastName());
		toSave.setUsername(user.getUsername());
		
		// TODO: User roles.
		// toSave.setUserRoles(new ArrayList<>());
		// TODO: Some sort of way of 'trusting' a user ornot?
		toSave.setEnabled(true);

		toSave.setPassword(passwordEncoder.encode(user.getPassword()));

		User saved = userRepository.save(toSave);
		
		if(saved!=null) {
			// should set the user here?
			return Response.ok().build();
		} else {
			return Response.badRequest().build();
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
