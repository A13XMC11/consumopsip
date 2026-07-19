package com.uisrael.consumopsip.model.dto.response;

import lombok.Data;

@Data
public class UbicacionResponseDto {
    private int idUbicacion;
    private String nombreUbicacion;
    private float latitudUbicacion;
    private float longitudUbicacion;
    private float radioMetrosUbicacion;
    private boolean estadoUbicacion;
}