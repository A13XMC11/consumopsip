package com.uisrael.consumopsip.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.uisrael.consumopsip.model.dto.request.MarcacionesRequestDto;
import com.uisrael.consumopsip.model.dto.response.MarcacionesResponseDto;
import com.uisrael.consumopsip.service.IMarcacionesService;

@Service
public class MarcacionesServiceImpl implements IMarcacionesService {

	private final WebClient webClient;

	public MarcacionesServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public List<MarcacionesResponseDto> listarMarcaciones() {
		return webClient.get().uri("/marcaciones").retrieve().bodyToFlux(MarcacionesResponseDto.class).collectList()
				.block();
	}

	@Override
	public void guardarMarcacion(MarcacionesRequestDto nuevaMarcacion) {
		webClient.post().uri("/marcaciones").bodyValue(nuevaMarcacion).retrieve().toBodilessEntity().block();
	}
}