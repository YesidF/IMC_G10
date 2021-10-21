package com.imc.apirest.converter;

import com.imc.apirest.dtos.UserResponseDTO;
import com.imc.apirest.dtos.UsuarioDTO;
import com.imc.apirest.models.Usuario;

import lombok.AllArgsConstructor;
@AllArgsConstructor
public class UsuarioConverter extends AbstractConverter<Usuario, UserResponseDTO>{
	
	private RolConverter rolConverter;
	private PerfilConverter perfilConverter;
	
	@Override
	public UserResponseDTO fromEntity(Usuario entity) {
		if(entity==null) return null;
		
		return UserResponseDTO.builder()
				.id(entity.getId())
				.rol(rolConverter.fromEntity(entity.getRol()))
				.nombre(entity.getNombre())
				.apellidos(entity.getApellidos())
				.telefono(entity.getTelefono())
				.email(entity.getEmail())
				.genero(entity.getGenero())
				.build();
	}

	@Override
	public Usuario fromDto(UserResponseDTO dto) {
		if(dto==null) return null;
		
		return Usuario.builder()
				.id(dto.getId())
				.rol(rolConverter.fromDto(dto.getRol()))
				.nombre(dto.getNombre())
				.apellidos(dto.getApellidos())
				.telefono(dto.getTelefono())
				.email(dto.getEmail())
				.genero(dto.getGenero())
				.build();
	}
	
	public Usuario fromDto(UsuarioDTO dto) {
		if(dto==null) return null;
		
		return Usuario.builder()
				.id(dto.getId())
				.perfil(perfilConverter.fromDto(dto.getPerfil()))
				.rol(rolConverter.fromDto(dto.getRol()))
				.nombre(dto.getNombre())
				.apellidos(dto.getApellidos())
				.telefono(dto.getTelefono())
				.email(dto.getEmail())
				.genero(dto.getGenero())
				.password(dto.getPassword())
				.build();
	}
}
