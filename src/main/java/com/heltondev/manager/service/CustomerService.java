package com.heltondev.manager.service;

import com.heltondev.manager.entity.Customer;
import com.heltondev.manager.entity.User;
import com.heltondev.manager.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	CustomerRepository _customerRepository;

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	public List<Customer> findAll() {
		LOGGER.info( "[CustomerService :: findAll] -> Searching for all users" );
		return _customerRepository.findAll();
	}

	public ResponseEntity<Customer> findById(long id) throws Exception {
		LOGGER.info( "[CustomerService :: findById] -> Searching for specific items by id" );
		Optional<Customer> customer = _customerRepository.findById(id);
		if ( customer.isPresent() )
			return customer
					.map( selected -> new ResponseEntity<>( selected, HttpStatus.OK ) )
					.orElseGet( () -> new ResponseEntity<>( HttpStatus.NOT_FOUND ) );
		else throw new Exception();
	}

	public Customer findByName(String name) {
		LOGGER.info( "[CustomerService :: findById] -> Searching for specific items by id" );
		String queryName = "Customer.findByName";
		Query query = em.createNamedQuery(queryName);
		query.setParameter( "name", name );
		com.heltondev.manager.entity.Customer user = ( com.heltondev.manager.entity.Customer ) query.getSingleResult();
		return ( Customer ) query.getSingleResult();
	}

	public Customer create(Customer payload) throws Exception {
		LOGGER.info( "[CustomerService :: create] -> Adding new user" );
		Long selectedId = findByName( payload.getName() ).getId();
		try {
			if (!_customerRepository.existsById( selectedId ) )
				return _customerRepository.save( payload );
			else
				throw new Exception("Customer already exists");
		} catch ( Exception e ) {
			throw new Exception(e);
		}
	}

	public Customer update(Customer payload, long id) throws Exception {

		LOGGER.info( "[CustomerService :: update] -> Updating existing user" );

		if ( _customerRepository.existsById( id ) ) {

			Optional<Customer> customer = _customerRepository.findById(id);

			if ( customer.isEmpty() ) {
				LOGGER.error("Exception: ID provided does not exist");
				throw new Exception();
			}

			payload.setId( customer.get().getId() );

			return _customerRepository.save( payload );

		} else
			throw new Exception();



	}

	public void delete( long id) throws Exception {
		LOGGER.info( "[CustomerService :: delete] -> Delete user" );
		Optional<Customer> account = _customerRepository.findById(id);
		if (account.isEmpty()) {
			ResponseEntity.notFound().build();
			return;
		}
		try {
			_customerRepository.deleteById( id );
		} catch ( Exception e ) {
			throw new Exception(e);
		}
	}
}
