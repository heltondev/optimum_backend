package com.heltondev.manager.controller;

import com.heltondev.manager.model.Customer;
import com.heltondev.manager.service.CustomerService;

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

import java.sql.Timestamp;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@ExtendWith( MockitoExtension.class )
public class CustomerControllerTest {

	private static Customer customerOne;
	private static Customer customerTwo;
	private static Customer customerThree;

	@Mock
	private CustomerService _customerService;

	@InjectMocks
	private CustomerController _customerController;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks( this );
	}

	@BeforeAll
	public static void init() {
		customerOne = new Customer("John Doe", new Timestamp(1L), "SP", "Sao Paulo" , "00000000", "00000000000",  new ArrayList<>() );
		customerTwo = new Customer("Rachel Doe", new Timestamp(2L), "CE", "Fortaleza" , "11111111", "11111111111",  new ArrayList<>() );
		customerThree = new Customer("Michael Doe", new Timestamp(3L), "SP", "Sao Paulo" , "00000000", "22222222222",  new ArrayList<>() );
	}

	@Test
	void findAll_whenNoRecord() {
		Mockito.when(_customerService.findAll()).thenReturn( Collections.emptyList() );
		assertThat(_customerController.findAll().size(), is(0));
		Mockito.verify(_customerService, Mockito.times(1)).findAll();
	}

	@Test
	void findAll_whenRecord() {
		Mockito.when(_customerService.findAll()).thenReturn( Arrays.asList(customerOne, customerTwo) );
		assertThat(_customerController.findAll().size(), is(2));
		Mockito.verify(_customerService, Mockito.times(1)).findAll();
	}

	@Test
	void create() throws Exception {
		ResponseEntity<Customer> p = _customerController.create(customerOne);
		Mockito.verify(_customerService, Mockito.times(1)).create(customerOne);
	}

	@Test
	void findById_WhenMatch() throws Exception {
		Mockito.when(_customerService.findById(1L)).thenReturn(ResponseEntity.of(Optional.of( customerOne )));
		ResponseEntity<Customer> p = _customerController.findById(1L);
		assertThat(p.getBody(), is(customerOne) );
	}

	@Test
	void findById_WhenNoMatch() throws Exception {
		Mockito.when(_customerService.findById(1L)).thenReturn(ResponseEntity.of( Optional.empty()));
		ResponseEntity<Customer> p = _customerController.findById(1L);
		assertThat(p.getStatusCode(), is( HttpStatus.NOT_FOUND));
	}

	@Test
	void update_WhenNotFound() throws Exception {
		Mockito.when(_customerService.findById(1L)).thenReturn(ResponseEntity.of( Optional.empty()));
		ResponseEntity<Customer> p = _customerController.update(1L, customerOne);
		assertThat(p.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}

	@Test
	void update_WhenFound() throws Exception{
		Mockito.when(_customerService.findById(1L)).thenReturn( ResponseEntity.of( Optional.of( customerOne )));
		// Since the Controller internally saves customerOne taking args of customerThree.
		Mockito.when(_customerService.update(customerOne, 1L)).thenReturn(customerThree);
		assertThat( Objects.requireNonNull( _customerController.update( 1L, customerThree ).getBody() ).getName(), is("Michael Doe"));
		Mockito.verify(_customerService, Mockito.times(1)).update(customerOne, 1L);
	}

	@Test
	void deleteById_WhenNotFound() throws Exception {
		Mockito.when(_customerService.findById(1L)).thenReturn( ResponseEntity.of( Optional.empty()));
		_customerController.deleteById(1L);
		Mockito.verify(_customerService, Mockito.times(0)).delete(1L);
	}

	@Test
	void deleteById_WhenFound() throws Exception {
		Mockito.when(_customerService.findById(1L)).thenReturn(ResponseEntity.of(Optional.of( customerOne )));
		_customerController.deleteById(1L);
		Mockito.verify(_customerService, Mockito.times(1)).delete(1L);
	}
}
