package com.uisrael.consumopsip.service;

import java.util.List;
import com.uisrael.consumopsip.model.dto.request.UbicacionRequestDto;
import com.uisrael.consumopsip.model.dto.response.UbicacionResponseDto;

public interface IUbicacionService {
	
    List<UbicacionResponseDto> listarUbicaciones();
    
    void guardarUbicacion(UbicacionRequestDto nuevaUbicacion);
}