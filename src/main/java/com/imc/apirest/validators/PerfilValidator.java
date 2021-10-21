package com.imc.apirest.validators;

import com.imc.apirest.exceptions.ValidateServiceException;
import com.imc.apirest.models.Perfil;

public class PerfilValidator {
	public static void save(Perfil perfil) {
		if(perfil.getDescripcion()==null || perfil.getDescripcion().trim().isEmpty()) {
			throw new ValidateServiceException("La descripción es requerida");
		}
		if(perfil.getDescripcion().length()>100) {
			throw new ValidateServiceException("La descripción no puede exceder de 100 carácteres ");
		}
		
		if(perfil.getArea()==null || perfil.getArea().trim().isEmpty()) {
			throw new ValidateServiceException("El area es requerida");
		}
		if(perfil.getArea().length()>100) {
			throw new ValidateServiceException("El area no puede exceder de 100 carácteres");
		}
		
	}
}
