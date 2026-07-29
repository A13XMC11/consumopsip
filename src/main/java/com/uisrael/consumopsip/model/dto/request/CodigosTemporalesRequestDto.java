package com.uisrael.consumopsip.model.dto.request;

import lombok.Data;

@Data
public class CodigosTemporalesRequestDto {
    private int idCodigo;
    private int idEmpleado;
    private String codigo;
    private String tipo;
    private boolean usado;
}