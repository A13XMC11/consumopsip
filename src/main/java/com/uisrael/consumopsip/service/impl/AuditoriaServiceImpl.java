package com.uisrael.consumopsip.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.uisrael.consumopsip.model.dto.request.AuditoriaRequestDto;
import com.uisrael.consumopsip.model.dto.response.AuditoriaResponseDto;
import com.uisrael.consumopsip.service.IAuditoriaService;

@Service
public class AuditoriaServiceImpl implements IAuditoriaService {

	private final WebClient webClient;

	public AuditoriaServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public List<AuditoriaResponseDto> listarAuditorias(String token) {
		return webClient.get().uri("/auditoria").header("Authorization", "Bearer " + token).retrieve()
				.bodyToFlux(AuditoriaResponseDto.class).collectList().block();
	}

	@Override
	public void guardarAuditoria(AuditoriaRequestDto nuevaAuditoria, String token) {
		webClient.post().uri("/auditoria").header("Authorization", "Bearer " + token).bodyValue(nuevaAuditoria)
				.retrieve().toBodilessEntity().block();
	}
}