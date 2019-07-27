package com.kios.app.agw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kios.app.agw.entities.User;
import com.kios.app.agw.repository.UserRepository;

@Service
public class AuthenticationService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	public User registerUser(User u) {
		//	Check if the user already exists	
		if(userRepository.findByEmail(u.getEmail()) != null) {
			return null;
		}
		else {
			User toSave = new User();
			toSave.setEmail(u.getEmail());
			// toSave.setUserRoles(new ArrayList<>());
			toSave.setEnabled(true);
			toSave.setPassword(passwordEncoder.encode(u.getPassword()));
			User saved = userRepository.save(toSave);
			return saved;
		}
	}
}
