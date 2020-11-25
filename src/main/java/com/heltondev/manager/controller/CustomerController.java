package com.heltondev.manager.controller;

import com.heltondev.manager.model.Customer;
import com.heltondev.manager.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RestController
@RequestMapping( { "/api/v1/customers" })
public class CustomerController {

	@Autowired
	private CustomerService _customerService;

	/**
	 * List all items from the database
	 * @return list of Customers
	 */
	@GetMapping
	public List<Customer> findAll() {return _customerService.findAll();}

	/**
	 * Retrieve the item from the database where id matches the to the criteria
	 * @param id Expected to receive an id type long as input parameter
	 * @return Returns List of Customer
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Customer> findById(@PathVariable( value = "id") long id) throws Exception {return _customerService.findById( id );}

	/**
	 * Retrieve the item from the database where id matches the to the criteria
	 * @param zipcode Expected to receive an zipcode type String as input parameter
	 * @return Returns List of Customer
	 */
	@GetMapping("/zipcode/{zipcode}")
	public List<Customer> findByCep(@PathVariable( value = "zipcode") String zipcode) throws Exception {return _customerService.findByCep( zipcode );}

	/**
	 * Retrieve the item from the database where id matches the to the criteria
	 * @param cpf Expected to receive an cpf type String as input parameter
	 * @return Returns List of Customer
	 */
	@GetMapping("/cpf/{cpf}")
	public List<Customer> findByCpf(@PathVariable( value = "cpf") String cpf) throws Exception {return _customerService.findByCpf( cpf );}

	/**
	 * Add a new item to the database
	 * @param payload Expected to receive a payload with proper format
	 * @return Returns the item inserted into the database
	 */
	@PostMapping
	public ResponseEntity<Customer> create( @Valid @RequestBody Customer payload ) throws Exception {return ResponseEntity.ok(_customerService.create( payload ));}

	/**
	 * Modify an item into the database
	 * @param id Expected to receive a Id as query parameter
	 * @param payload Expected to receive a payload with proper format
	 * @return Returns the updated item
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Customer> update(@PathVariable(value = "id") long id, @Valid @RequestBody Customer payload) throws Exception {return ResponseEntity.ok( _customerService.update(payload, id) );
	}

	/**
	 * Delete an item from the database
	 * @param id Expected to receive a Id as query parameter
	 */
	@DeleteMapping("/{id}")
	public void deleteById( @PathVariable(value = "id") long id) throws Exception {_customerService.delete( id );}
}
