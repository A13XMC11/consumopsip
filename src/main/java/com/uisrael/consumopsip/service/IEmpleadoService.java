package com.uisrael.consumopsip.service;

import java.util.List;

import com.uisrael.consumopsip.model.dto.request.EmpleadoRequestDto;
import com.uisrael.consumopsip.model.dto.response.EmpleadoResponseDto;

public interface IEmpleadoService {
	
	List<EmpleadoResponseDto> listarEmpleados();
	
	void guardarEmpleado(EmpleadoRequestDto nuevoEmpleado);
	
}
