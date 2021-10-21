package com.imc.apirest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.imc.apirest.security.RestAuthenticationEntryPoint;
import com.imc.apirest.security.TokenAuthenticationFilter;

@Configuration
@EnableWebSecurity //Identifica que esta clase va ser la configuración para spring Security
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Bean
	public PasswordEncoder createPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
		.cors() //PermiteQueUnaAppWebPuedaConsumirUnaApiQueNoEstéEnElMismoDominio
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //CadaPeticiónSeManejaSinEstado-->DebeRecorrerElProcesoDeAutenticación
			.and()
		.csrf() //DeshabilitandoDispositivoDeSeguridadPermiteQueUnaAppWebPuedaConsumirUnaApiQueNoEstéEnElMismoDominio
			.disable()
		.formLogin() //DeshabilitaLaFormaDeLogearnosPorMedioDeUnForm
			.disable()
		.httpBasic() //DesabilitarHttpBasic --> OtroMétodoDeAutenticacion
			.disable()
		.exceptionHandling()
			.authenticationEntryPoint(new RestAuthenticationEntryPoint()) //ControlarLosErroresDeAutenticacion
			.and()
		.authorizeRequests()
			.antMatchers(
					"/",
					"/error",
					"/favicon.ico",
					"/**/*.png",
					"/**/*.gif",
					"/**/*.svg",
					"/**/*.jpg",
					"/**/*.html",
					"/**/*.css",
					"/**/*.js",
					"/**/*.woff2"
					)
				.permitAll()
			//urlsQuePuedenSerAccedidasSinToken
			.antMatchers(
					"/users/login",
					"/v2/api-docs",
					"/webjars/**",
					"/swagger-resources/**"
					)
				.permitAll()
			.anyRequest()
				.authenticated();
			
		http.addFilterBefore(createTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public TokenAuthenticationFilter createTokenAuthenticationFilter() {
		return new TokenAuthenticationFilter();
	}
}
