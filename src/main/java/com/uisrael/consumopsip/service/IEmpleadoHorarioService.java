package com.uisrael.consumopsip.service;

import java.util.List;

import com.uisrael.consumopsip.model.dto.request.EmpleadoHorarioRequestDto;
import com.uisrael.consumopsip.model.dto.response.EmpleadoHorarioResponseDto;

public interface IEmpleadoHorarioService {
	List<EmpleadoHorarioResponseDto> listarEmpleadoHorarios();
    void guardarEmpleadoHorario(EmpleadoHorarioRequestDto nuevoEmpleadoHorario);
}
