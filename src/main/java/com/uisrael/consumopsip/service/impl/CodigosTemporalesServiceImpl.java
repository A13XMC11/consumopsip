package com.uisrael.consumopsip.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.uisrael.consumopsip.model.dto.request.CodigosTemporalesRequestDto;
import com.uisrael.consumopsip.model.dto.response.CodigosTemporalesResponseDto;
import com.uisrael.consumopsip.service.ICodigosTemporalesService;

@Service
public class CodigosTemporalesServiceImpl implements ICodigosTemporalesService {

    private final WebClient webClient;

    public CodigosTemporalesServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<CodigosTemporalesResponseDto> listarCodigosTemporales() {
        return webClient.get()
                .uri("/codigosTemporales")
                .retrieve()
                .bodyToFlux(CodigosTemporalesResponseDto.class)
                .collectList()
                .block();
    }

    @Override
    public void guardarCodigoTemporal(CodigosTemporalesRequestDto nuevoCodigo) {
        webClient.post()
                .uri("/codigosTemporales")
                .bodyValue(nuevoCodigo)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
