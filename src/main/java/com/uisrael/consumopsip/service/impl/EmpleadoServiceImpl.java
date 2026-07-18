package com.uisrael.consumopsip.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.consumopsip.model.dto.request.EmpleadoRequestDto;
import com.uisrael.consumopsip.model.dto.response.EmpleadoResponseDto;
import com.uisrael.consumopsip.service.IEmpleadoService;
@Service
public class EmpleadoServiceImpl implements IEmpleadoService{

	private final WebClient webClient;

	public EmpleadoServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public List<EmpleadoResponseDto> listarEmpleados() {
		return webClient.get().uri("/empleado").retrieve().bodyToFlux(EmpleadoResponseDto.class).collectList().block();
	}

	@Override
	public void guardarEmpleado(EmpleadoRequestDto nuevoEmpleado) {
		webClient.post().uri("/empleado").bodyValue(nuevoEmpleado).retrieve().toBodilessEntity().block();
	}
}
