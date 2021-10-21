package com.imc.apirest.validators;


import com.imc.apirest.exceptions.ValidateServiceException;
import com.imc.apirest.models.Usuario;

public class UsuarioValidator {
	public static void save(Usuario usuario) {
		if(usuario.getNombre()==null || usuario.getNombre().trim().isEmpty()) {
			throw new ValidateServiceException("El nombre es requerido");
		}
		if(usuario.getNombre().length()>100) {
			throw new ValidateServiceException("El nombre de usuario no puede exceder de 100 carácteres");
		}
		if(usuario.getApellidos()==null||usuario.getApellidos().trim().isEmpty()) {
			throw new ValidateServiceException("Los apellidos son requeridos");
		}
		if(usuario.getApellidos().length()>100) {
			throw new ValidateServiceException("Los apellidos no pueden exceder de 100 carácteres");
		}
		if(usuario.getTelefono()==null||usuario.getTelefono().trim().isEmpty()) {
			throw new ValidateServiceException("El teléfono es requerido");
		}
		if(usuario.getTelefono().length()>20) {
			throw new ValidateServiceException("El teléfono no puede exceder de 20 carácteres");
		}
		if(usuario.getEmail()==null||usuario.getEmail().trim().isEmpty()) {
			throw new ValidateServiceException("El email es requerido");
		}
		if(usuario.getEmail().length()>100) {
			throw new ValidateServiceException("El email no puede exceder de 100 carácteres");
		}
		if(usuario.getPassword()==null||usuario.getPassword().trim().isEmpty()) {
			throw new ValidateServiceException("El password es requerido");
		}
		if(usuario.getPassword().length()>30) {
			throw new ValidateServiceException("El password no puede exceder de 30 carácteres");
		}
		
		if(usuario.getGenero()==null || usuario.getGenero().trim().isEmpty()) {
			throw new ValidateServiceException("El genero es requerido");
		}
		if(usuario.getGenero().length()>10) {
			throw new ValidateServiceException("El genero no puede exceder de 10 carácteres");
		}
		if(!usuario.getGenero().trim().toUpperCase().equals("MASCULINO")) {
			if(!usuario.getGenero().trim().toUpperCase().equals("FEMENINO")) {
				throw new ValidateServiceException("El genero debe ser masculino o femenino"+usuario.getGenero().trim().toUpperCase());
			}
		}
	}
}
