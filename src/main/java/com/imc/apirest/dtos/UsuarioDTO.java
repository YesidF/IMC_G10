package com.imc.apirest.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UsuarioDTO {
	private Long id;
	private PerfilDTO perfil;
	private RolDTO rol;
	private String nombre;
	private String apellidos;
	private String telefono;
	private String genero;
	private String email;
	private String password;
}
