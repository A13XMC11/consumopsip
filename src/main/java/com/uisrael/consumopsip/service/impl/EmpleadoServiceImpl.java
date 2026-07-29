package com.uisrael.consumopsip.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.consumopsip.model.dto.request.EmpleadoRequestDto;
import com.uisrael.consumopsip.model.dto.response.EmpleadoResponseDto;
import com.uisrael.consumopsip.service.IEmpleadoService;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

	private final WebClient webClient;

	public EmpleadoServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public List<EmpleadoResponseDto> listarEmpleados(String token) {
		return webClient.get().uri("/empleado").header("Authorization", "Bearer " + token).retrieve()
				.bodyToFlux(EmpleadoResponseDto.class).collectList().block();
	}

	@Override
	public void guardarEmpleado(EmpleadoRequestDto nuevoEmpleado, String token) {
		if (nuevoEmpleado.getIdEmpleado() != 0
				&& (nuevoEmpleado.getContrasenaEmpleado() == null || nuevoEmpleado.getContrasenaEmpleado().isBlank())) {
			EmpleadoResponseDto actual = buscarPorId(nuevoEmpleado.getIdEmpleado(), token);
			nuevoEmpleado.setContrasenaEmpleado(actual.getContrasenaEmpleado());
		}
		if (nuevoEmpleado.getIdEmpleado() != 0
				&& (nuevoEmpleado.getNumeroDocumento() == null || nuevoEmpleado.getNumeroDocumento().isBlank())) {
			EmpleadoResponseDto actual = buscarPorId(nuevoEmpleado.getIdEmpleado(), token);
			nuevoEmpleado.setTipoDocumento(actual.getTipoDocumento());
			nuevoEmpleado.setNumeroDocumento(actual.getNumeroDocumento());
		}
		webClient.post().uri("/empleado").header("Authorization", "Bearer " + token).bodyValue(nuevoEmpleado).retrieve()
				.toBodilessEntity().block();
	}

	@Override
	public EmpleadoResponseDto buscarPorId(int idEmpleado, String token) {
		return listarEmpleados(token).stream().filter(empleado -> empleado.getIdEmpleado() == idEmpleado).findFirst()
				.orElse(null);
	}

	@Override
	public void desactivarEmpleado(int idEmpleado, String token) {
		EmpleadoResponseDto actual = buscarPorId(idEmpleado, token);
		EmpleadoRequestDto dto = new EmpleadoRequestDto();
		dto.setIdEmpleado(actual.getIdEmpleado());
		dto.setIdRol(actual.getIdRol());
		dto.setNombreEmpleado(actual.getNombreEmpleado());
		dto.setApellidosEmpleado(actual.getApellidosEmpleado());
		dto.setCorreoEmpleado(actual.getCorreoEmpleado());
		dto.setTipoDocumento(actual.getTipoDocumento());
		dto.setNumeroDocumento(actual.getNumeroDocumento());
		dto.setEstadoEmpleado(false);
		guardarEmpleado(dto, token);
	}
}
