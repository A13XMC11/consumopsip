package com.uisrael.consumopsip.service;

import java.util.List;
import com.uisrael.consumopsip.model.dto.request.MarcacionesRequestDto;
import com.uisrael.consumopsip.model.dto.response.MarcacionesResponseDto;

public interface IMarcacionesService {
	
	List<MarcacionesResponseDto> listarMarcaciones(String token);

	void guardarMarcacion(MarcacionesRequestDto nuevaMarcacion, String token);

	void solicitarMarcacion(String token, String tipo);

	MarcacionesResponseDto registrarMarcacion(String token, double lat, double lng);
}