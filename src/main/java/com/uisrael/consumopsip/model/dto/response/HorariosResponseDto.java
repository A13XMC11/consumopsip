package com.uisrael.consumopsip.model.dto.response;

import java.time.LocalTime;
import lombok.Data;

@Data
public class HorariosResponseDto {
    private int idHorario;
    private String nombre;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private int toleranciaMinutos;
    private boolean estadoHorario;
}