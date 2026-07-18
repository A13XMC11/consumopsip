package com.uisrael.consumopsip.model.dto.response;

import java.util.Date;

import lombok.Data;

@Data
public class EmpleadoHorarioResponseDto {
    private int idAsignacion;
    private int idEmpleado;
    private int idHorario;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean estadoEmpleadoHorario;
}
