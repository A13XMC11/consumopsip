package com.uisrael.consumopsip.model.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
public class MarcacionesRequestDto {
    private int idMarcaciones;
    private int idEmpleado;
    private int idCodigo;
    private int idUbicacion;
    private String tipo;
    private LocalDate fechaMarcacion;
    private LocalTime horaMarcacion;
    private float latitud;
    private float longitud;
    private boolean dentroRango;
    private boolean dentroHorario;
    private boolean valida;
    private String observacion;
}
