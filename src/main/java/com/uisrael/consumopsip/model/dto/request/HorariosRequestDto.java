package com.uisrael.consumopsip.model.dto.request;

import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class HorariosRequestDto {
    private int idHorario;
    private String nombre;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horaEntrada;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horaSalida;
    private int toleranciaMinutos;
    private boolean estadoHorario;
}