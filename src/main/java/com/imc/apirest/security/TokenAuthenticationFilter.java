package com.imc.apirest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.imc.apirest.exceptions.NoDataFoundException;
import com.imc.apirest.models.Usuario;
import com.imc.apirest.repositories.UsuarioRepository;
import com.imc.apirest.services.UsuarioService;

import lombok.extern.slf4j.Slf4j;

//cada petición que se haga a spring boot se ejecuta esta clase
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter{
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	)throws ServletException, IOException {
		try {
			String jwt = getJwtFromRequest(request);
			
			//validar token
			if(StringUtils.hasText(jwt) && usuarioService.validateToken(jwt)) {
				String username = usuarioService.getUsernameFromToken(jwt);
				Long idUserName = Long.parseLong(username);
				Usuario user = usuarioRepo.findById(idUserName)
						.orElseThrow(()->new NoDataFoundException("No existe el usuario"));
				
				UserPrincipal principal = UserPrincipal.create(user);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken
						(
						principal,
						null,
						principal.getAuthorities()
						);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			log.error("Error al autenticar al usuario",e);
		}
		
		filterChain.doFilter(request, response);
	}
	
	public String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7,bearerToken.length());
		}
		return null;
	}
}
