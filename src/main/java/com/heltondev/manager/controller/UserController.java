package com.heltondev.manager.controller;

import com.heltondev.manager.model.User;
import com.heltondev.manager.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping( { "/api/v1/users" })
public class UserController {

	@Autowired
	private UserService _userService;

	/**
	 * List all items from the database
	 * @return list of Users
	 */
	@GetMapping
	public ResponseEntity<List<User>> findAll() {return _userService.findAll();}

	/**
	 * Retrieve the item from the database where id matches the to the criteria
	 * @param id Expected to receive an id type long as input parameter
	 * @return Returns List of User
	 */
	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable( value = "id") long id) throws Exception {return _userService.findById( id );}

	/**
	 * Add a new item to the database
	 * @param payload Expected to receive a payload with proper format
	 * @return Returns the item inserted into the database
	 */
	@PostMapping
	public ResponseEntity<User> create( @Valid @RequestBody User payload ) throws Exception {return ResponseEntity.ok(_userService.create( payload ));}

	/**
	 * Modify an item into the database
	 * @param id Expected to receive a Id as query parameter
	 * @param payload Expected to receive a payload with proper format
	 * @return Returns the updated item
	 */
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable(value = "id") long id, @Valid @RequestBody User payload) throws Exception {return ResponseEntity.ok( _userService.update(payload, id) );
	}

	/**
	 * Delete an item from the database
	 * @param id Expected to receive a Id as query parameter
	 */
	@DeleteMapping("/{id}")
	public void deleteById( @PathVariable(value = "id") long id) throws Exception {_userService.delete( id );}
}
