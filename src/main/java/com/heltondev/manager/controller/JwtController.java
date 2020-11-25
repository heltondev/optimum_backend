package com.heltondev.manager.controller;

import com.heltondev.manager.model.*;
import com.heltondev.manager.service.JwtService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping( { "/authenticate" })
public class JwtController {

	private static final Logger LOGGER = LoggerFactory.getLogger( JwtController.class);

	@Autowired
	private JwtService _jwtService;

	@PostMapping
	public ResponseEntity<?> createAuthenticationToken( @RequestBody Jwt.JwtRequestBuilder authenticationRequest) throws Exception {
		return ResponseEntity.ok( new Jwt.JwtResponseBuilder(_jwtService.generateToken( authenticationRequest )) );
	}
}
