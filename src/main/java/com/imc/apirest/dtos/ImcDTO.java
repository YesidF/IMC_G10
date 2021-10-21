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
public class ImcDTO {
	private Long id;
	private UserResponseDTO usuario;
	private Double talla;
	private Double masa;
	private String reg_imc;
	private Double imc;
}
