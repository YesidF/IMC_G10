package com.imc.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imc.apirest.converter.PerfilConverter;
import com.imc.apirest.dtos.PerfilDTO;
import com.imc.apirest.models.Perfil;
import com.imc.apirest.services.PerfilService;
import com.imc.apirest.utils.WrapperResponse;

@RestController
@RequestMapping("perfiles")
public class PerfilController {
	@Autowired
	private PerfilService perfilService;
	@Autowired
	private PerfilConverter perfilConverter;
	
	@PostMapping
	public ResponseEntity<WrapperResponse<PerfilDTO>> create(@RequestBody PerfilDTO perfilDto){
		Perfil newPerfil = this.perfilService.save(this.perfilConverter.fromDto(perfilDto));
		return new WrapperResponse<PerfilDTO>(true,"success",this.perfilConverter.fromEntity(newPerfil)).createResponse();
	}
	
	@PutMapping
	public ResponseEntity<WrapperResponse<PerfilDTO>> update(@RequestBody PerfilDTO perfilDto){
		Perfil perfilActualizado = this.perfilService.save(this.perfilConverter.fromDto(perfilDto));
		return new WrapperResponse<PerfilDTO>(true,"success",this.perfilConverter.fromEntity(perfilActualizado)).createResponse();
	}
	
	@GetMapping
	public ResponseEntity<WrapperResponse<List<PerfilDTO>>> findAll(
			@RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(name = "pageSize", required=false, defaultValue = "5") int pageSize
	){
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<Perfil> perfiles = this.perfilService.findAll(page);
		List<PerfilDTO> perfilesDto = this.perfilConverter.fromEntity(perfiles);
		return new WrapperResponse<List<PerfilDTO>>(true,"success",perfilesDto).createResponse();
	}
	
	@GetMapping(value = "{id}")
	public ResponseEntity<WrapperResponse<PerfilDTO>> finById(@PathVariable("id") Long perfilId){
		PerfilDTO perfilExist = this.perfilConverter.fromEntity(perfilService.findById(perfilId));
		return new WrapperResponse<PerfilDTO>(true,"success",perfilExist).createResponse();
	}
	
	@DeleteMapping(value = "{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long perfilId){
		this.perfilService.delete(perfilId);
		return new WrapperResponse<>(true,"success",null).createResponse();
	}
}
