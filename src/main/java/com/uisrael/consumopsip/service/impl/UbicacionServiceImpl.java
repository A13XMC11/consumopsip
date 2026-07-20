package com.uisrael.consumopsip.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.uisrael.consumopsip.model.dto.request.UbicacionRequestDto;
import com.uisrael.consumopsip.model.dto.response.UbicacionResponseDto;
import com.uisrael.consumopsip.service.IUbicacionService;

@Service
public class UbicacionServiceImpl implements IUbicacionService {

	private final WebClient webClient;

	public UbicacionServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public List<UbicacionResponseDto> listarUbicaciones(String token) {
		return webClient.get().uri("/ubicacion")
				.header("Authorization", "Bearer " + token)
				.retrieve().bodyToFlux(UbicacionResponseDto.class).collectList()
				.block();
	}

	@Override
	public void guardarUbicacion(UbicacionRequestDto nuevaUbicacion, String token) {
		webClient.post().uri("/ubicacion")
				.header("Authorization", "Bearer " + token)
				.bodyValue(nuevaUbicacion).retrieve().toBodilessEntity().block();
	}
}
