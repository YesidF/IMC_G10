package com.imc.apirest.models;

import java.util.Objects;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name="usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "FK_PERFIL", nullable = false)
	private Perfil perfil;
	
	@ManyToOne
	@JoinColumn(name = "FK_ROL", nullable = false)
	private Rol rol;
	
	@Column(name="nombre", nullable=false, length = 100)
	private String nombre;
	
	@Column(name="apelldos", nullable=false, length = 100)
	private String apellidos;
	
	@Column(name="telefono", nullable=false, length = 20)
	private String telefono;
	
	@Column(name="genero", nullable = false, length=10)
	private String genero;
	
	@Column(name="email", nullable=false, length = 100)
	private String email;
	
	@Column(name="password", nullable = false, length = 150)
	private String password;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
