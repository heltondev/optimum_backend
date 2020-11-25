package com.heltondev.manager.model;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

public class Jwt implements Serializable{

	@Data
	public static class JwtRequestBuilder implements Serializable {

		private static final long serialVersionUID = 5926468583005150707L;

		@NonNull private String username;
		@NonNull private String password;

		public JwtRequestBuilder(String username, String password) {
			setUsername(username);
			setPassword(password);
		}
	}

	@Data
	public static class JwtResponseBuilder implements Serializable {
		private static final long serialVersionUID = -8091879091924046844L;
		private final String token;
	}
}


