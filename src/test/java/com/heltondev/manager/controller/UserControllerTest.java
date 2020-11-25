package com.heltondev.manager.controller;

import com.heltondev.manager.model.User;
import com.heltondev.manager.service.UserService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@ExtendWith( MockitoExtension.class )
public class UserControllerTest {

	private static User userOne;
	private static User userTwo;
	private static User userThree;

	@Mock
	private UserService _userService;

	@InjectMocks
	private UserController _userController;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks( this );
	}

	@BeforeAll
	public static void init() {
		userOne = new User("John Doe", "john@doe.com", "123456");
		userTwo = new User("Rachel Doe", "rachel@doe.com", "123456");
		userThree = new User("Jay Doe", "jay@doe.com", "123456");
	}

	@Test
	void findAll_whenNoRecord() {
		Mockito.when(_userService.findAll()).thenReturn( Collections.emptyList() );
		assertThat(_userController.findAll().size(), is(0));
		Mockito.verify(_userService, Mockito.times(1)).findAll();
	}

	@Test
	void findAll_whenRecord() {
		Mockito.when(_userService.findAll()).thenReturn( Arrays.asList(userOne, userTwo) );
		assertThat(_userController.findAll().size(), is(2));
		Mockito.verify(_userService, Mockito.times(1)).findAll();
	}

	@Test
	void create() throws Exception {
		ResponseEntity<User> p = _userController.create(userOne);
		Mockito.verify(_userService, Mockito.times(1)).create(userOne);
	}

	@Test
	void findById_WhenMatch() throws Exception {
		Mockito.when(_userService.findById(1L)).thenReturn(ResponseEntity.of(Optional.of( userOne )));
		ResponseEntity<User> p = _userController.findById(1L);
		assertThat(p.getBody(), is(userOne) );
	}

	@Test
	void findById_WhenNoMatch() throws Exception {
		Mockito.when(_userService.findById(1L)).thenReturn(ResponseEntity.of( Optional.empty()));
		ResponseEntity<User> p = _userController.findById(1L);
		assertThat(p.getStatusCode(), is( HttpStatus.NOT_FOUND));
	}

	@Test
	void update_WhenNotFound() throws Exception {
		Mockito.when(_userService.findById(1L)).thenReturn(ResponseEntity.of( Optional.empty()));
		ResponseEntity<User> p = _userController.update(1L, userOne);
		assertThat(p.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}

	@Test
	void update_WhenFound() throws Exception{
		Mockito.when(_userService.findById(1L)).thenReturn(ResponseEntity.of(Optional.of( userOne )));
		// Since the Controller internally saves userOne taking args of userThree.
		Mockito.when(_userService.update(userOne, 1L)).thenReturn(userThree);
		assertThat( Objects.requireNonNull( _userController.update( 1L, userThree ).getBody() ).getPassword(), is("654321"));
		Mockito.verify(_userService, Mockito.times(1)).update(userOne, 1L);
	}

	@Test
	void deleteById_WhenNotFound() throws Exception {
		Mockito.when(_userService.findById(1L)).thenReturn( ResponseEntity.of( Optional.empty()));
		_userController.deleteById(1L);
		Mockito.verify(_userService, Mockito.times(0)).delete(1L);
	}

	@Test
	void deleteById_WhenFound() throws Exception {
		Mockito.when(_userService.findById(1L)).thenReturn(ResponseEntity.of(Optional.of( userOne )));
		_userController.deleteById(1L);
		Mockito.verify(_userService, Mockito.times(1)).delete(1L);
	}
}
