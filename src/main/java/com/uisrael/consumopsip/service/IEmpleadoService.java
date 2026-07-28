package com.uisrael.consumopsip.service;

import java.util.List;

import com.uisrael.consumopsip.model.dto.request.EmpleadoRequestDto;
import com.uisrael.consumopsip.model.dto.response.EmpleadoResponseDto;

public interface IEmpleadoService {
	
	List<EmpleadoResponseDto> listarEmpleados(String token);

	void guardarEmpleado(EmpleadoRequestDto nuevoEmpleado, String token);
	
	EmpleadoResponseDto buscarPorId(int idEmpleado, String token);

	void desactivarEmpleado(int idEmpleado, String token);

}
