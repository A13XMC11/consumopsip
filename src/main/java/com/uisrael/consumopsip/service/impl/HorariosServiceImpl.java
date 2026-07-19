package com.uisrael.consumopsip.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.uisrael.consumopsip.model.dto.request.HorariosRequestDto;
import com.uisrael.consumopsip.model.dto.response.HorariosResponseDto;
import com.uisrael.consumopsip.service.IHorariosService;

@Service
public class HorariosServiceImpl implements IHorariosService {

	private final WebClient webClient;

	public HorariosServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public List<HorariosResponseDto> listarHorarios(String token) {
		return webClient.get().uri("/horarios")
				.header("Authorization", "Bearer " + token)
				.retrieve().bodyToFlux(HorariosResponseDto.class).collectList().block();
	}

	@Override
	public void guardarHorario(HorariosRequestDto nuevoHorario, String token) {
		webClient.post().uri("/horarios")
				.header("Authorization", "Bearer " + token)
				.bodyValue(nuevoHorario).retrieve().toBodilessEntity().block();
	}
}