package com.imc.apirest.converter;

import java.time.format.DateTimeFormatter;

import com.imc.apirest.dtos.ImcDTO;
import com.imc.apirest.models.Imc;

import lombok.AllArgsConstructor;
@AllArgsConstructor
public class ImcConverter extends AbstractConverter<Imc, ImcDTO>{
	private UsuarioConverter usuarioConverter;
	private DateTimeFormatter dateTimeFormat;
	@Override
	public ImcDTO fromEntity(Imc entity) {
		if(entity==null) return null;
		
		return ImcDTO.builder()
				.id(entity.getId())
				.usuario(this.usuarioConverter.fromEntity(entity.getUsuario()))
				.talla(entity.getTalla())
				.masa(entity.getMasa())
				.reg_imc(entity.getReg_imc().format(dateTimeFormat))
				.imc(entity.getImc())
				.build();
	}

	@Override
	public Imc fromDto(ImcDTO dto) {
		return Imc.builder()
				.id(dto.getId())
				.usuario(this.usuarioConverter.fromDto(dto.getUsuario()))
				.talla(dto.getTalla())
				.masa(dto.getMasa())
				.imc(dto.getImc())
				.build();
	}
}
