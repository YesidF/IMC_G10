package com.imc.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imc.apirest.exceptions.GeneralServiceException;
import com.imc.apirest.exceptions.NoDataFoundException;
import com.imc.apirest.exceptions.ValidateServiceException;
import com.imc.apirest.models.Perfil;
import com.imc.apirest.repositories.PerfilRepository;
import com.imc.apirest.validators.PerfilValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PerfilService {
	@Autowired
	private PerfilRepository perfilRepo;
	@Transactional
	public Perfil save(Perfil perfil) {
		try {
			PerfilValidator.save(perfil);
			if(perfil.getId()==null) {
				Perfil newPerfil = this.perfilRepo.save(perfil);
				return newPerfil;
			}else {
				Perfil perfilExist = this.perfilRepo.findById(perfil.getId())
						.orElseThrow(()->new NoDataFoundException("El perfil no existe"));
				perfilExist.setDescripcion(perfil.getDescripcion());
				perfilExist.setArea(perfil.getArea());
				return perfilExist;
			}
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	
	//GetPerfil
	public Perfil findById(Long id) {
		try {
			return this.perfilRepo.findById(id).orElseThrow(()->new NoDataFoundException("El Perfil no existe"));
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	
	//GetPerfiles
	public List<Perfil> findAll(Pageable page){
		try {
			return perfilRepo.findAll(page).toList();
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	
	//DeletePerfil
	@Transactional
	public void delete(Long id) {
		try {
			Perfil perfil = this.perfilRepo.findById(id).orElseThrow(()->new NoDataFoundException("El perfil no existe"));
			this.perfilRepo.delete(perfil);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
}
