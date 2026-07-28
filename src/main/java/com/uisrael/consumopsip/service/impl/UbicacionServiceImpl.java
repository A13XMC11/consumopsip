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

	@Override
	public UbicacionResponseDto buscarPorId(int idUbicacion, String token) {
		return listarUbicaciones(token).stream().filter(u -> u.getIdUbicacion() == idUbicacion).findFirst()
				.orElse(null);
	}

	@Override
	public void desactivarUbicacion(int idUbicacion, String token) {
		UbicacionResponseDto actual = buscarPorId(idUbicacion, token);
		UbicacionRequestDto dto = new UbicacionRequestDto();
		dto.setIdUbicacion(actual.getIdUbicacion());
		dto.setNombreUbicacion(actual.getNombreUbicacion());
		dto.setLatitudUbicacion(actual.getLatitudUbicacion());
		dto.setLongitudUbicacion(actual.getLongitudUbicacion());
		dto.setRadioMetrosUbicacion(actual.getRadioMetrosUbicacion());
		dto.setEstadoUbicacion(false);
		guardarUbicacion(dto, token);
	}
}
