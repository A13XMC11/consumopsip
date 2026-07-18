package com.uisrael.consumopsip.model.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RolResponseDto {
	private int idRol;
	private String nombreRol;
	private String descripcionRol;
	private LocalDateTime creadoRol;
}
