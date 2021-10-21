package com.imc.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imc.apirest.exceptions.GeneralServiceException;
import com.imc.apirest.exceptions.NoDataFoundException;
import com.imc.apirest.exceptions.ValidateServiceException;
import com.imc.apirest.models.Rol;
import com.imc.apirest.repositories.RolRepository;
import com.imc.apirest.validators.RolValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RolService {
	@Autowired
	private RolRepository rolRepo;
	
	//SaveRol
	@Transactional
	public Rol save(Rol rol) {
		try {
			RolValidator.save(rol);
			if(rol.getId()==null) {
				Rol newRol = rolRepo.save(rol);
				return newRol;
			}else {
				Rol rolExist = rolRepo.findById(rol.getId())
						.orElseThrow(()->new NoDataFoundException("El rol no existe"));
				rolExist.setDescripcion(rol.getDescripcion());
				return rolExist;
			}
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	
	//GetRol
	public Rol findById(Long id) {
		try {
			return this.rolRepo.findById(id).orElseThrow(()->new NoDataFoundException("El rol no existe"));
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	
	//GetRoles
	public List<Rol> findAll(Pageable page){
		try {
			return rolRepo.findAll(page).toList();
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	
	//DeleteRole
	@Transactional
	public void delete(Long id) {
		try {
			Rol rol = this.rolRepo.findById(id).orElseThrow(()->new NoDataFoundException("El rol no existe"));
			this.rolRepo.delete(rol);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
}
