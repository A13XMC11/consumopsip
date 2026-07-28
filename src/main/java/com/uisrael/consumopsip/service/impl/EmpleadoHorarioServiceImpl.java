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
    public List<EmpleadoHorarioResponseDto> listarEmpleadoHorarios(String token) {
        return webClient.get()
                .uri("/empleadoHorario")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(EmpleadoHorarioResponseDto.class)
                .collectList()
                .block();
    }

    @Override
    public void guardarEmpleadoHorario(EmpleadoHorarioRequestDto nuevoEmpleadoHorario, String token) {
        webClient.post()
                .uri("/empleadoHorario")
                .header("Authorization", "Bearer " + token)
                .bodyValue(nuevoEmpleadoHorario)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Override
    public EmpleadoHorarioResponseDto buscarPorId(int idAsignacion, String token) {
        return listarEmpleadoHorarios(token).stream()
                .filter(eh -> eh.getIdAsignacion() == idAsignacion)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void eliminarEmpleadoHorario(int idAsignacion, String token) {
        webClient.delete()
                .uri("/empleadoHorario/{idAsignacion}", idAsignacion)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}