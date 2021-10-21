package com.imc.apirest.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.imc.apirest.models.Usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPrincipal implements UserDetails{

	private Usuario usuario;
	private Collection<? extends GrantedAuthority> authorities;
	
	public static UserPrincipal create(Usuario usuario) {
		List<GrantedAuthority> authorities = Collections
				.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
		return new UserPrincipal(usuario,authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.usuario.getPassword();
	}

	@Override
	public String getUsername() {
		return this.usuario.getId()+"";
	}
	
	public static Usuario getCurrentUser() {
		UserPrincipal principal = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return principal.getUsuario();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
