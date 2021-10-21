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


import com.imc.apirest.converter.RolConverter;
import com.imc.apirest.dtos.RolDTO;
import com.imc.apirest.models.Rol;
import com.imc.apirest.services.RolService;
import com.imc.apirest.utils.WrapperResponse;

@RestController
@RequestMapping("roles")
public class RolController {
	@Autowired
	RolService rolService;
	@Autowired
	RolConverter rolConverter;
	
	@PostMapping
	public ResponseEntity<WrapperResponse<RolDTO>> create(@RequestBody RolDTO roldto) {
		Rol newRol = rolService.save(rolConverter.fromDto(roldto));
		return new WrapperResponse<RolDTO>(true,"success",this.rolConverter.fromEntity(newRol)).createResponse();
		
	}
	
	@PutMapping
	public ResponseEntity<WrapperResponse<RolDTO>> update(@RequestBody RolDTO rolDto){
		Rol rolActualizado = this.rolService.save(this.rolConverter.fromDto(rolDto));
		return new WrapperResponse<RolDTO>(true,"success",this.rolConverter.fromEntity(rolActualizado)).createResponse();
	}
	
	@GetMapping(value = "{id}")
	public ResponseEntity<WrapperResponse<RolDTO>> findById(@PathVariable("id") Long rolID) {
		Rol rolExist = this.rolService.findById(rolID);
		return new WrapperResponse<RolDTO>(true,"success",this.rolConverter.fromEntity(rolExist)).createResponse();
	}
	
	@GetMapping
	public ResponseEntity<WrapperResponse<List<RolDTO>>> findAll(
			@RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(name = "pageSize", required=false, defaultValue = "5") int pageSize
	){
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<Rol> roles = this.rolService.findAll(page);
		List<RolDTO> rolesDto = this.rolConverter.fromEntity(roles);
		return new WrapperResponse<List<RolDTO>>(true,"success",rolesDto).createResponse();
	}
	
	@DeleteMapping(value = "{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long rolID){
		this.rolService.delete(rolID);
		return new WrapperResponse<>(true,"success",null).createResponse();
	}
}

