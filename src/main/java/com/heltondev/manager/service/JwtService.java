package com.heltondev.manager.service;

import com.heltondev.manager.config.JwtTokenUtil;
import com.heltondev.manager.model.Jwt;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

@Service
public class JwtService implements UserDetailsService {


	private static final Logger LOGGER = LoggerFactory.getLogger( JwtService.class);

	@Autowired
	private AuthenticationManager _authenticationManager;

	@Autowired
	private JwtTokenUtil _jwtTokenUtil;

	@Autowired
	private UserService _userService;

	@SneakyThrows
	@Override
	public UserDetails loadUserByUsername( String username) throws UsernameNotFoundException {

		com.heltondev.manager.model.User user = _userService.findByUsername( username );

		// UserDetails Password should be passed as it is stored in the database (SHA256)
		if (user.getUsername().equals(username)) {
			return new User(user.getUsername(), BCrypt.hashpw( user.getPassword(), BCrypt.gensalt(10) ),
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

	public String generateToken( @RequestBody Jwt.JwtRequestBuilder authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = _userService
				.loadUserByUsername(authenticationRequest.getUsername());

		return _jwtTokenUtil.generateToken(userDetails);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			_authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch ( DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch ( BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
