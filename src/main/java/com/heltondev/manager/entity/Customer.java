package com.heltondev.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

@Data
@Entity
@Table(name = "m_customers")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners( AuditingEntityListener.class)
@NamedQueries( {
		@NamedQuery(name = "Customer.findByName", query = "SELECT u FROM Customer u WHERE u.name = :name")
} )
public class Customer {

	private static final Logger LOGGER = LoggerFactory.getLogger(Customer.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NonNull
	private String name;

	@NonNull
	private Timestamp dateOfBirth;

	@NonNull
	private String state;

	@NonNull
	private String city;

	@NonNull
	private String zipcode;

	@NonNull
	private String cpf;

	private ArrayList<Object> contacts;

	public Customer() {
		super();
	}

	public Customer(String name, Timestamp dateOfBirth, String state, String city, String zipcode, String cpf, ArrayList<Object> contacts) {
		setName( name );
		setDateOfBirth( dateOfBirth );
		setState( state );
		setCity( city );
		setZipcode( zipcode );
		setCpf( cpf );
		setContacts( contacts );
	}

	/**
	 * Returns a string representation of the object. In general, the {@code toString} method returns a string that
	 * "textually represents" this object. The result should be a concise but informative representation that is easy for
	 * a person to read. It is recommended that all subclasses override this method.
	 * <p>
	 * The {@code toString} method for class {@code Object} returns a string consisting of the name of the class of which
	 * the object is an instance, the at-sign character `{@code @}', and the unsigned hexadecimal representation of the
	 * hash code of the object. In other words, this method returns a string equal to the value of:
	 * <blockquote>
	 * <pre>
	 * getClass().getName() + '@' + Integer.toHexString(hashCode())
	 * </pre></blockquote>
	 *
	 * @return a string representation of the object.
	 */
	@Override
	public String toString() {
		String value = "";

		try {
			ObjectMapper om = new ObjectMapper();
			value = om.writeValueAsString(this);
		} catch ( JsonProcessingException e ) {
			LOGGER.error("Exception serializing to json in Accounts", e);
		}

		return value;
	}

}
