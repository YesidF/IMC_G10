package com.imc.apirest.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imc.apirest.exceptions.GeneralServiceException;
import com.imc.apirest.exceptions.NoDataFoundException;
import com.imc.apirest.exceptions.ValidateServiceException;
import com.imc.apirest.models.Imc;
import com.imc.apirest.models.Usuario;
import com.imc.apirest.repositories.ImcRepository;
import com.imc.apirest.repositories.UsuarioRepository;
import com.imc.apirest.security.UserPrincipal;
import com.imc.apirest.validators.ImcValidator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImcService {
	@Autowired
	private ImcRepository imcRepo;
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Transactional
	public Imc save(Imc imc) {
		try {
			ImcValidator.Save(imc);
			//obtener el usuario autenticado
			Usuario userCurrent = UserPrincipal.getCurrentUser();
			
			//Usuario userCurrent = usuarioRepo.getById((long) 3);
			if(imc.getId()==null) {
				//setear el usuario autenticado
				imc.setUsuario(userCurrent);
				imc.setReg_imc(LocalDateTime.now());
				//calcular IMC
				imc.setImc(this.calcularImc(imc.getTalla(), imc.getMasa()));
				Imc newImc = this.imcRepo.save(imc);
				return newImc;
			}else {
				Imc imcExist = imcRepo.findById(imc.getId()).orElseThrow(()->new NoDataFoundException("El Imc no existe"));
				imcExist.setTalla(imc.getTalla());
				imcExist.setMasa(imc.getMasa());
				//calcularIMC
				imcExist.setImc(this.calcularImc(imc.getTalla(), imc.getMasa()));
				return imcExist;
			}
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	
	public Imc findById(Long imcId) {
		try {
			return this.imcRepo.findById(imcId).orElseThrow(()->new NoDataFoundException("IMC no existe"));
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	
	/*public List<Imc> findAll(Pageable page) {
		try {
			return this.imcRepo.findAll(page).toList();
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}*/
	
	public Page<Imc> findAll(Pageable page) {
		try {
			return this.imcRepo.findAll(page);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	
	public List<Imc> getAll() {
		try {
			return this.imcRepo.findAll();
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	
	private Double calcularImc(Double estatura, Double peso) {
		//Estatura en metros, peso en Kg
		return peso/Math.pow(estatura, 2);
	}
}
