package com.uisrael.consumopsip.model.dto.request;

import lombok.Data;

@Data
public class UbicacionRequestDto {
	private int idUbicacion;
	private String nombreUbicacion;
	private float latitudUbicacion;
	private float longitudUbicacion;
	private float radioMetrosUbicacion;
	private boolean estadoUbicacion;
}