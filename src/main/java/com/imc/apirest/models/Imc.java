package com.imc.apirest.models;

import java.time.LocalDateTime;

import javax.persistence.*;

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
@Entity
@Table(name ="imc")
public class Imc {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "FK_USUARIO", updatable = false)
	private Usuario usuario;
	
	@Column(name="talla", nullable = false)
	private Double talla;
	
	@Column(name="masa", nullable = false)
	private Double masa;
	
	@Column(name="reg_imc", nullable = false, updatable = false)
	private LocalDateTime reg_imc;
	
	@Column(name="imc", nullable = false)
	private Double imc;
}
