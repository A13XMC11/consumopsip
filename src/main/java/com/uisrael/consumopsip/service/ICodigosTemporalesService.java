package com.uisrael.consumopsip.service;

import java.util.List;
import com.uisrael.consumopsip.model.dto.request.CodigosTemporalesRequestDto;
import com.uisrael.consumopsip.model.dto.response.CodigosTemporalesResponseDto;

public interface ICodigosTemporalesService {
    List<CodigosTemporalesResponseDto> listarCodigosTemporales(String token);
    void guardarCodigoTemporal(CodigosTemporalesRequestDto nuevoCodigo, String token);
}