package com.heltondev.manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name = "m_users")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners( AuditingEntityListener.class)
@NamedQueries( {
		@NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
} )
public class User {

	private static final Logger LOGGER = LoggerFactory.getLogger(User.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	private String name;

	private String username;
	
	private String password;

	@CreationTimestamp
	@Column(name = "created_at")
	private Timestamp createdAt;

	@CreationTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	public User( String name, String username, String password ) {
		setName( name );
		setUsername( username );
		setPassword( password );
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
