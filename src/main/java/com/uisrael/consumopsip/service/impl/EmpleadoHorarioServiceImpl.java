package com.uisrael.consumopsip.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.uisrael.consumopsip.model.dto.request.EmpleadoHorarioRequestDto;
import com.uisrael.consumopsip.model.dto.response.EmpleadoHorarioResponseDto;
import com.uisrael.consumopsip.service.IEmpleadoHorarioService;

@Service
public class EmpleadoHorarioServiceImpl implements IEmpleadoHorarioService {

    private final WebClient webClient;

    public EmpleadoHorarioServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<EmpleadoHorarioResponseDto> listarEmpleadoHorarios() {
        return webClient.get()
                .uri("/empleadoHorario")
                .retrieve()
                .bodyToFlux(EmpleadoHorarioResponseDto.class)
                .collectList()
                .block();
    }

    @Override
    public void guardarEmpleadoHorario(EmpleadoHorarioRequestDto nuevoEmpleadoHorario) {
        webClient.post()
                .uri("/empleadoHorario")
                .bodyValue(nuevoEmpleadoHorario)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}