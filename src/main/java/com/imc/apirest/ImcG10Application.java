package com.imc.apirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ImcG10Application {

	public static void main(String[] args) {
		SpringApplication.run(ImcG10Application.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				//registry.addMapping("/users").allowedOrigins("http://localhost:8080");
				//registry.addMapping("/users/**").allowedOrigins("http://localhost:8080");
				//registry.addMapping("/**").allowedOrigins("http://localhost:8080").allowedMethods("POST");
				registry.addMapping("/**").allowedOrigins("https://app-vue-83860.web.app").allowedMethods("*").allowedHeaders("*");
				//registry.addMapping("/**").allowedOrigins("http://localhost:8080").allowedMethods("*").allowedHeaders("*");
			}
		};
	}

}
