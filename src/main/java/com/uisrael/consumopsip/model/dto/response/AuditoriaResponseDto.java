package com.uisrael.consumopsip.model.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AuditoriaResponseDto {
    private long idAuditoria;
    private int idEmpleado;
    private String accion;
    private String tablaAfectada;
    private int registroId;
    private String detalle;
    private String ip;
    private LocalDateTime fechaHora;
}