package com.uisrael.consumopsip.model.dto.request;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AuditoriaRequestDto {
	private long idAuditoria;
	private int idEmpleado;
	private String accion;
	private String tablaAfectada;
	private int registroId;
	private String detalle;
	private String ip;
	private LocalDateTime fechaHora;
}