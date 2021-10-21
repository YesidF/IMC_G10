package com.imc.apirest.converter;

import com.imc.apirest.dtos.PerfilDTO;
import com.imc.apirest.models.Perfil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PerfilConverter extends AbstractConverter<Perfil, PerfilDTO>{

	@Override
	public PerfilDTO fromEntity(Perfil entity) {
		if(entity==null) return null;
		return PerfilDTO.builder()
				.id(entity.getId())
				.descripcion(entity.getDescripcion())
				.area(entity.getArea())
				.build();
	}

	@Override
	public Perfil fromDto(PerfilDTO dto) {
		if(dto==null) return null;
		return Perfil.builder()
				.id(dto.getId())
				.descripcion(dto.getDescripcion())
				.area(dto.getArea())
				.build();
	}
	
}
