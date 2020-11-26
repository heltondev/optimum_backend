package com.heltondev.manager.service;

import com.heltondev.manager.model.Customer;
import com.heltondev.manager.model.User;
import com.heltondev.manager.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

@Service
public class CustomerService implements Serializable {

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
		LOGGER.info( "[CustomerService :: findByName] -> Searching for specific items by name" );
		String queryName = "Customer.findByName";
		Query query = em.createNamedQuery(queryName);
		query.setParameter( "name", name );
		return ( Customer ) query.getSingleResult();
	}

	public List<Customer> findByCpf( String cpf) {
		LOGGER.info( "[CustomerService :: findByCpf] -> Searching for specific items by CPF" );
		String queryName = "Customer.findByCpf";
		Query query = em.createNamedQuery(queryName);
		query.setParameter( "cpf", cpf );
		return (List<Customer> ) query.getResultList();
	}

	public List<Customer> findByCep( String zipcode) {
		LOGGER.info( "[CustomerService :: findByCep] -> Searching for specific items by CEP" );
		String queryName = "Customer.findByCep";
		Query query = em.createNamedQuery(queryName);
		query.setParameter( "zipcode", zipcode );
		return (List<Customer> ) query.getResultList();
	}

	public Customer create(Customer payload) throws Exception {
		LOGGER.info( "[CustomerService :: create] -> Adding new user" );
//		Optional<Long> selectedId = Optional.of( findByName( payload.getName() ).getId() );
//		try {
//			if (!_customerRepository.existsById( selectedId.get() ) )
//				return _customerRepository.save( payload );
//			else
//				throw new Exception("Customer already exists");
//		} catch ( Exception e ) {
//			throw new Exception(e);
//		}

		return _customerRepository.save( payload );
	}

	public Customer update(Customer payload, long id) throws Exception {

		LOGGER.info( "[CustomerService :: update] -> Updating existing user" );

		if ( _customerRepository.existsById( id ) ) {

			Optional<Customer> customer = _customerRepository.findById(id);

			if ( customer.isEmpty() ) {
				LOGGER.error("Exception: ID provided does not exist");
				throw new Exception();
			}

			payload.setId( id );

			if ( payload.getContacts().size() < 1 ) throw new Exception("Should have at least 1 contact added");

			customer.get().setName( payload.getName() );
			customer.get().setDateOfBirth( payload.getDateOfBirth() );
			customer.get().setState( payload.getState() );
			customer.get().setCity( payload.getCity() );
			customer.get().setZipcode( payload.getZipcode() );
			customer.get().setCpf( payload.getCpf() );
			customer.get().setContacts(new ArrayList<>( payload.getContacts() ) );
			customer.get().setId( payload.getId() );

			return _customerRepository.save( customer.get() );

		} else
			throw new Exception("Exception: ID provided does not exist");



	}

	public void delete( long id) throws Exception {
		LOGGER.info( "[CustomerService :: delete] -> Delete user" );
		Optional<Customer> customer = _customerRepository.findById(id);
		if (customer.isEmpty()) {
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
