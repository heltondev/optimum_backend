package com.heltondev.manager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class AppConfig  implements WebMvcConfigurer {
	@Override
	public void addCorsMappings( CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:4200","http://localhost:4200/api/v1/customers","http://localhost:4200/api/v1/users")
				.allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE");
	}
}
