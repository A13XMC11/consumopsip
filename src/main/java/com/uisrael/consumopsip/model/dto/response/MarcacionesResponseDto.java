package com.uisrael.consumopsip.model.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
public class MarcacionesResponseDto {

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
