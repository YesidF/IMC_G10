package com.imc.apirest.converter;

import com.imc.apirest.dtos.RolDTO;
import com.imc.apirest.models.Rol;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RolConverter extends AbstractConverter<Rol, RolDTO>{

	@Override
	public RolDTO fromEntity(Rol entity) {
		
		if(entity==null) return null;
		
		return RolDTO.builder()
				.id(entity.getId())
				.descripcion(entity.getDescripcion())
				.build();
	}

	@Override
	public Rol fromDto(RolDTO dto) {
		if(dto==null) return null;
		return Rol.builder()
				.id(dto.getId())
				.descripcion(dto.getDescripcion())
				.build();
	}

}
