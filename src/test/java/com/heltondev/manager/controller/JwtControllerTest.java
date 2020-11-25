package com.heltondev.manager.controller;

import com.heltondev.manager.model.Jwt;
import com.heltondev.manager.service.JwtService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@ExtendWith( MockitoExtension.class )
public class JwtControllerTest {
	private static Jwt.JwtRequestBuilder userDetailsOne;
	private static Jwt.JwtRequestBuilder userDetailsTwo;
	private static Jwt.JwtRequestBuilder userDetailsThree;

	@Mock
	private JwtService _jwtService;

	@InjectMocks
	private JwtController _jwtController;
	private UserController _userController;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks( this );
	}

	@BeforeAll
	public static void init() {
		userDetailsOne = new Jwt.JwtRequestBuilder( "test@test.com", "test" );
		userDetailsTwo = new Jwt.JwtRequestBuilder( "test@test.com", "test" );;
	}

	@Test
	void findByUsername_whenRecord() throws Exception {
		Mockito.when(userDetailsOne).thenReturn(userDetailsTwo);
		assertThat(_jwtController.createAuthenticationToken( userDetailsOne ), is(2));
	}

	@Test
	void findByUsername_whenNoRecord() throws Exception {
		Mockito.when(userDetailsThree).thenReturn(userDetailsThree);
		assertThat(_jwtController.createAuthenticationToken( userDetailsThree ), is( 0 ));
	}
}
