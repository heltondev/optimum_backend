package com.heltondev.manager.service;

import com.heltondev.manager.model.User;
import com.heltondev.manager.repository.UserRepository;
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
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository _userRepository;

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	public List<User> findAll() {
		LOGGER.info( "[UserService :: findAll] -> Searching for all users" );
		return _userRepository.findAll();
	}

	public ResponseEntity<User> findById(long id) throws Exception {
		LOGGER.info( "[UserService :: findById] -> Searching for specific items by id" );
		Optional<User> user = _userRepository.findById(id);
		if ( user.isPresent() )
			return user
						.map( selected -> new ResponseEntity<>( selected, HttpStatus.OK ) )
						.orElseGet( () -> new ResponseEntity<>( HttpStatus.NOT_FOUND ) );
		else throw new Exception();
	}

	public User findByUsername(String username) {
		LOGGER.info( "[UserService :: findById] -> Searching for specific items by id" );
		String queryName = "User.findByUsername";
		Query query = em.createNamedQuery(queryName);
		query.setParameter( "username", username );
		return ( User ) query.getSingleResult();
	}

	public UserDetails loadUserByUsername( String username) {

		User user = findByUsername( username );

		// UserDetails Password should be passed as it is stored in the database (SHA256)
		if (user.getUsername().equals(username)) {
			return new org.springframework.security.core.userdetails.User(user.getUsername(), BCrypt.hashpw( user.getPassword(), BCrypt.gensalt(10) ),
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

	public User create(User payload) throws Exception {
		LOGGER.info( "[UserService :: create] -> Adding new user" );
		Long selectedId = findByUsername( payload.getUsername() ).getId();
		try {
			if (!_userRepository.existsById( selectedId ) )
				return _userRepository.save( payload );
			else
				throw new Exception("User already exists");
		} catch ( Exception e ) {
			throw new Exception(e);
		}
	}

	public User update(User payload, long id) throws Exception {

		LOGGER.info( "[UserService :: update] -> Updating existing user" );

		if ( _userRepository.existsById( id ) ) {

			Optional<User> user = _userRepository.findById(id);

			if ( user.isEmpty() || (payload.getName().isBlank() && payload.getPassword().isBlank()) ) {
				LOGGER.error("Exception: ID provided does not exist");
				throw new Exception();
			}

			if ( !payload.getName().isBlank() )
				user.get().setName( payload.getName() );

			if ( !payload.getPassword().isBlank() )
				user.get().setPassword( payload.getPassword() );

			if ( user.get().getCreatedAt() == null || user.get().getCreatedAt().getTime() < 1)
				payload.setCreatedAt( new Timestamp(System.currentTimeMillis()) );

			payload.setId( id );
			payload.setCreatedAt( user.get().getCreatedAt() );
			payload.setUpdatedAt( new Timestamp(System.currentTimeMillis()) );

			return _userRepository.save( payload );

		} else
			throw new Exception();



	}

	public void delete( long id) throws Exception {
		LOGGER.info( "[UserService :: delete] -> Delete user" );
		Optional<User> account = _userRepository.findById(id);
		if (account.isEmpty()) {
			ResponseEntity.notFound().build();
			return;
		}
		try {
			_userRepository.deleteById( id );
		} catch ( Exception e ) {
			throw new Exception(e);
		}
	}

	public void addInitialUser() throws Exception {
		LOGGER.info( "[UserService :: Creating Initial User]" );
		List<User> users = _userRepository.findAll();

		if ( users.isEmpty() ) {
			User user = new User();
			user.setName( "John Doe" );
			user.setUsername( "test@test.com" );
			user.setPassword( "8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92" );

			_userRepository.save( user );
		}
	}

}
