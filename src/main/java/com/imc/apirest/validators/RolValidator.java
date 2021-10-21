package com.imc.apirest.validators;

import com.imc.apirest.exceptions.ValidateServiceException;
import com.imc.apirest.models.Rol;

public class RolValidator {
	public static void save(Rol rol) {
		if(rol.getDescripcion()==null || rol.getDescripcion().trim().isEmpty()) {
			throw new ValidateServiceException("La descripción es requerida");			
		}
		if(rol.getDescripcion().length()>100) {
			throw new ValidateServiceException("La descripción no puede exceder de 100 carácteres ");			
		}
	}
}
