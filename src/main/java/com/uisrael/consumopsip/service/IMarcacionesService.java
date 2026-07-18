package com.uisrael.consumopsip.service;

import java.util.List;
import com.uisrael.consumopsip.model.dto.request.MarcacionesRequestDto;
import com.uisrael.consumopsip.model.dto.response.MarcacionesResponseDto;

public interface IMarcacionesService {
	
    List<MarcacionesResponseDto> listarMarcaciones();
    
    void guardarMarcacion(MarcacionesRequestDto nuevaMarcacion);
}