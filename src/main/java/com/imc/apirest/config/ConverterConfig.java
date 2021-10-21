package com.imc.apirest.config;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.imc.apirest.converter.ImcConverter;
import com.imc.apirest.converter.PerfilConverter;
import com.imc.apirest.converter.RolConverter;
import com.imc.apirest.converter.UsuarioConverter;

@Configuration
public class ConverterConfig {
	@Value("${config.datetimeFormat}")
	private String dateTimeFormat;
	
	@Bean
	public RolConverter getRolConverter() {
		return new RolConverter();
	}
	
	@Bean
	public PerfilConverter getPerfilConverter() {
		return new PerfilConverter();
	}
	
	@Bean
	public UsuarioConverter getUsuarioConverter() {
		return new UsuarioConverter(this.getRolConverter(),this.getPerfilConverter());
	}
	
	@Bean
	public ImcConverter getImcConverter() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(this.dateTimeFormat);
		return new ImcConverter(this.getUsuarioConverter(), format);
	}
}
