package com.uisrael.consumopsip.model.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CodigosTemporalesResponseDto {
    private int idCodigo;
    private int idEmpleado;
    private String codigo;
    private String tipo;
    private LocalDateTime generadoEn;
    private LocalDateTime expiraEn;
    private boolean usado;
}