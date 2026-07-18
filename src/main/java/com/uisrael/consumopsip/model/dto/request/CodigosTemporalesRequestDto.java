package com.uisrael.consumopsip.model.dto.request;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CodigosTemporalesRequestDto {
    private int idCodigo;
    private int idEmpleado;
    private String codigo;
    private String tipo;
    private LocalDateTime generadoEn;
    private LocalDateTime expiraEn;
    private boolean usado;
}