package com.uisrael.consumopsip.service;

import java.util.List;
import com.uisrael.consumopsip.model.dto.request.HorariosRequestDto;
import com.uisrael.consumopsip.model.dto.response.HorariosResponseDto;

public interface IHorariosService {
    List<HorariosResponseDto> listarHorarios();
    void guardarHorario(HorariosRequestDto nuevoHorario);
}