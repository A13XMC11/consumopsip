package com.uisrael.consumopsip.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.uisrael.consumopsip.model.dto.response.MarcacionesResponseDto;
import com.uisrael.consumopsip.service.IMarcacionesService;

@Service
public class MarcacionesServiceImpl implements IMarcacionesService {

	private final WebClient webClient;

	public MarcacionesServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public List<MarcacionesResponseDto> listarMarcaciones(String token) {
		return webClient.get().uri("/marcaciones").header("Authorization", "Bearer " + token).retrieve()
				.bodyToFlux(MarcacionesResponseDto.class).collectList().block();
	}

	@Override
	public void solicitarMarcacion(String token, String tipo) {
		webClient.post().uri(uriBuilder -> uriBuilder.path("/marcaciones/solicitar").queryParam("tipo", tipo).build())
				.header("Authorization", "Bearer " + token).retrieve().toBodilessEntity().block();
	}

	@Override
	public MarcacionesResponseDto registrarMarcacion(String token, double lat, double lng) {
		return webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/marcaciones/registrar").queryParam("token", token)
						.queryParam("lat", lat).queryParam("lng", lng).build())
				.retrieve().bodyToMono(MarcacionesResponseDto.class).block();
	}
}