package com.uisrael.consumopsip.service;

import java.util.List;

import com.uisrael.consumopsip.model.dto.request.HorariosRequestDto;
import com.uisrael.consumopsip.model.dto.response.HorariosResponseDto;

public interface IHorariosService {

	List<HorariosResponseDto> listarHorarios(String token);

	void guardarHorario(HorariosRequestDto nuevoHorario, String token);

	HorariosResponseDto buscarPorId(int idHorario, String token);

	void desactivarHorario(int idHorario, String token);
}