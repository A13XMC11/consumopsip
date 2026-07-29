package com.uisrael.consumopsip.model.dto.request;

import lombok.Data;

@Data
public class RestablecerContrasenaRequestDto {
	private String token;
	private String contrasenaNueva;
}
