package com.uisrael.consumopsip.model.dto.response;

import lombok.Data;

@Data
public class EmpleadoResponseDto {
	private int idEmpleado;
	private int idRol;
	private String nombreEmpleado;
	private String apellidosEmpleado;
	private String correoEmpleado;
	private String contrasenaEmpleado;
	private String tipoDocumento;
	private String numeroDocumento;
	private boolean estadoEmpleado;
}
