package com.uisrael.consumopsip.model.dto.request;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RolRequestDto {
    private int idRol;
    private String nombreRol;
    private String descripcionRol;
    private LocalDateTime creadoRol;
}