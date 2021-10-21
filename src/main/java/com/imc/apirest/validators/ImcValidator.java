package com.imc.apirest.validators;

import com.imc.apirest.exceptions.ValidateServiceException;
import com.imc.apirest.models.Imc;

public class ImcValidator {
	
	public static void Save(Imc imc) {
		if(imc.getTalla()==null) {
			throw new ValidateServiceException("La talla es requerido");
		}
		if(imc.getTalla() <= 0 ) {
			throw new ValidateServiceException("La Talla no puede ser un valor negativo");
		}
		if(imc.getMasa() == null ) {
			throw new ValidateServiceException("La Masa es requerido");
		}
		if(imc.getMasa() <= 0 ) {
			throw new ValidateServiceException("La Masa no puede ser un valor negativo");
		}
	}

}
