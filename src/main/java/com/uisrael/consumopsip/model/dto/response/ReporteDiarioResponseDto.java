package com.uisrael.consumopsip.model.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
public class ReporteDiarioResponseDto {
	private int idReporte;
	private int idEmpleado;
	private LocalDate fechaReporte;
	private LocalTime horaEntrada;
	private LocalTime horaSalida;
	private boolean tardanzaReporte;
	private int minutosTardanza;
	private boolean marcacionIncompleta;
}
