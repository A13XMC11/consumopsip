package com.uisrael.consumopsip.model.dto.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class EmpleadoHorarioRequestDto {
    private int idAsignacion;
    private int idEmpleado;
    private int idHorario;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;
    private boolean estadoEmpleadoHorario;
    private boolean forzarReemplazo;
}
