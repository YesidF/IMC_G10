package com.imc.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imc.apirest.converter.ImcConverter;
import com.imc.apirest.dtos.ImcDTO;
import com.imc.apirest.models.Imc;
import com.imc.apirest.services.ImcService;
import com.imc.apirest.utils.WrapperResponse;

@RestController
@RequestMapping("imc")
public class ImcController {
	@Autowired
	ImcConverter imcConverter;
	@Autowired
	ImcService imcService;
	
	@PostMapping
	public ResponseEntity<WrapperResponse<ImcDTO>> save(@RequestBody ImcDTO imcDto){
		Imc newImc =this.imcService.save(this.imcConverter.fromDto(imcDto));
		return new WrapperResponse<ImcDTO>(true,"success",this.imcConverter.fromEntity(newImc)).createResponse();		
	}
	
	@PutMapping
	public ResponseEntity<WrapperResponse<ImcDTO>> update(@RequestBody ImcDTO imcDto){
		Imc imcActualizado =this.imcService.save(this.imcConverter.fromDto(imcDto));
		return new WrapperResponse<ImcDTO>(true,"success",this.imcConverter.fromEntity(imcActualizado)).createResponse();		
	}
	
	@GetMapping(value = "{id}")
	public ResponseEntity<WrapperResponse<ImcDTO>> findById(@PathVariable("id") Long idImc){
		ImcDTO imcExist = this.imcConverter.fromEntity(this.imcService.findById(idImc));
		return new WrapperResponse<ImcDTO>(true,"success",imcExist).createResponse();
	}
	
	/*@GetMapping
	public ResponseEntity<WrapperResponse<List<ImcDTO>>> findAll(
			@RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(name = "pageSize", required=false, defaultValue = "5") int pageSize
	){
		Pageable page = PageRequest.of(pageNumber,pageSize);
		List<Imc> imcs = this.imcService.findAll(page);
		List<ImcDTO> imcsDto = this.imcConverter.fromEntity(imcs);
		return new WrapperResponse<List<ImcDTO>>(true,"success",imcsDto).createResponse();		
	}*/
	
	@GetMapping
	public ResponseEntity<WrapperResponse<Page<Imc>>> findAll(
			@RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(name = "pageSize", required=false, defaultValue = "5") int pageSize
	){
		Pageable page = PageRequest.of(pageNumber,pageSize);
		Page<Imc> imcs = this.imcService.findAll(page);
		return new WrapperResponse<Page<Imc>>(true,"success",imcs).createResponse();		
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<WrapperResponse<List<ImcDTO>>> getAll(){
		List<Imc> imcs = this.imcService.getAll();
		List<ImcDTO> imcsDto = this.imcConverter.fromEntity(imcs);
		return new WrapperResponse<List<ImcDTO>>(true,"success",imcsDto).createResponse();	
	}
	
}
