package com.uisrael.consumopsip.model.dto.request;

import lombok.Data;

@Data
public class EmpleadoRequestDto {
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
