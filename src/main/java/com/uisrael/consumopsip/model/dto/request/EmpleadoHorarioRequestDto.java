package com.uisrael.consumopsip.model.dto.request;

import java.util.Date;

import lombok.Data;

@Data
public class EmpleadoHorarioRequestDto {
    private int idAsignacion;
    private int idEmpleado;
    private int idHorario;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean estadoEmpleadoHorario;
}
