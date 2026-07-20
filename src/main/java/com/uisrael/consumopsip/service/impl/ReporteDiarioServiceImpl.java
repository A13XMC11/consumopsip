package com.uisrael.consumopsip.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.uisrael.consumopsip.model.dto.request.ReporteDiarioRequestDto;
import com.uisrael.consumopsip.model.dto.response.ReporteDiarioResponseDto;
import com.uisrael.consumopsip.service.IReporteDiarioService;

@Service
public class ReporteDiarioServiceImpl implements IReporteDiarioService {

	private final WebClient webClient;

	public ReporteDiarioServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public List<ReporteDiarioResponseDto> listarReportesDiarios(String token) {
		return webClient.get().uri("/reporteDiario").header("Authorization", "Bearer " + token).retrieve()
				.bodyToFlux(ReporteDiarioResponseDto.class).collectList().block();
	}

	@Override
	public void guardarReporteDiario(ReporteDiarioRequestDto nuevoReporte, String token) {
		webClient.post().uri("/reporteDiario").header("Authorization", "Bearer " + token).bodyValue(nuevoReporte)
				.retrieve().toBodilessEntity().block();
	}
}