package com.uisrael.consumopsip.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.uisrael.consumopsip.model.dto.request.RolRequestDto;
import com.uisrael.consumopsip.model.dto.response.RolResponseDto;
import com.uisrael.consumopsip.service.IRolService;

@Service
public class RolServiceImpl implements IRolService {

	private final WebClient webClient;

	public RolServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public List<RolResponseDto> listarRoles(String token) {
		return webClient.get().uri("/rol").header("Authorization", "Bearer " + token).retrieve()
				.bodyToFlux(RolResponseDto.class).collectList().block();
	}

	@Override
	public void guardarRol(RolRequestDto nuevoRol, String token) {
		webClient.post().uri("/rol").header("Authorization", "Bearer " + token).bodyValue(nuevoRol).retrieve()
				.toBodilessEntity().block();
	}
}