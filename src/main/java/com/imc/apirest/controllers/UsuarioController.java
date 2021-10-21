package com.imc.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imc.apirest.converter.UsuarioConverter;
import com.imc.apirest.dtos.LoginRequestDTO;
import com.imc.apirest.dtos.LoginResponseDTO;
import com.imc.apirest.dtos.UserResponseDTO;
import com.imc.apirest.dtos.UsuarioDTO;
import com.imc.apirest.models.Usuario;
import com.imc.apirest.services.UsuarioService;
import com.imc.apirest.utils.WrapperResponse;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("users")
public class UsuarioController {
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	UsuarioConverter usuarioConverter;
	
	//@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping
	public ResponseEntity<WrapperResponse<UserResponseDTO>> create(@RequestBody UsuarioDTO usuarioDto){
		Usuario usuario = this.usuarioService.save(this.usuarioConverter.fromDto(usuarioDto));
		return new WrapperResponse<UserResponseDTO>(true,"success",this.usuarioConverter.fromEntity(usuario)).createResponse();
	}
	
	@PutMapping
	public ResponseEntity<WrapperResponse<UserResponseDTO>> update(@RequestBody UsuarioDTO usuarioDto){
		Usuario usuarioActualizado = this.usuarioService.save(this.usuarioConverter.fromDto(usuarioDto));
		return new WrapperResponse<UserResponseDTO>(true,"success",this.usuarioConverter.fromEntity(usuarioActualizado)).createResponse();
	}
	
	@GetMapping(value = "{id}")
	public ResponseEntity<WrapperResponse<UserResponseDTO>> findById(@PathVariable("id") Long usuarioId) {
		Usuario usuarioExist = this.usuarioService.findById(usuarioId);
		return new WrapperResponse<UserResponseDTO>(true,"success",this.usuarioConverter.fromEntity(usuarioExist)).createResponse();
	}
	
	@GetMapping
	public ResponseEntity<WrapperResponse<List<UserResponseDTO>>> findAll(
			@RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(name = "pageSize", required=false, defaultValue = "5") int pageSize
	){
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<Usuario> usuarios = this.usuarioService.findAll(page);
		List<UserResponseDTO> usuariosDto = this.usuarioConverter.fromEntity(usuarios);
		return new WrapperResponse<List<UserResponseDTO>>(true,"success",usuariosDto).createResponse();
	}
	
	@DeleteMapping(value = "{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long usuarioId){
		this.usuarioService.delete(usuarioId);
		return new WrapperResponse<>(true,"success",null).createResponse();
	}
	
	@PostMapping(value="/login")
	@CrossOrigin
	public ResponseEntity<WrapperResponse<LoginResponseDTO>> login(@RequestBody LoginRequestDTO request){
		LoginResponseDTO response = this.usuarioService.login(request);
		return new WrapperResponse<LoginResponseDTO>(true,"success",response).createResponse();
	}
}
