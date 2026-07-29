package com.uisrael.consumopsip.model.dto.request;

import lombok.Data;

@Data
public class CambiarContrasenaRequestDto {
	private String contrasenaActual;
	private String contrasenaNueva;
}
