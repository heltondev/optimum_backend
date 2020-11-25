package com.heltondev.manager.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class Contact {

	private static final Logger LOGGER = LoggerFactory.getLogger(Contact.class);

	private String email;

	private String phone;

	private String skypeId;

	public Contact() {
		super();
	}

	public Contact( @NonNull String email, String phone, String skypeId ) {
		setEmail( email );
		setPhone( phone );
		setPhone( skypeId );
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
