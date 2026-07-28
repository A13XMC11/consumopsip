package com.uisrael.consumopsip.service;

import java.util.List;
import com.uisrael.consumopsip.model.dto.request.UbicacionRequestDto;
import com.uisrael.consumopsip.model.dto.response.UbicacionResponseDto;

public interface IUbicacionService {
	
	List<UbicacionResponseDto> listarUbicaciones(String token);

	void guardarUbicacion(UbicacionRequestDto nuevaUbicacion, String token);

	UbicacionResponseDto buscarPorId(int idUbicacion, String token);

	void desactivarUbicacion(int idUbicacion, String token);
}