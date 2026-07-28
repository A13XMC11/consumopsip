package com.uisrael.consumopsip.service;

import java.util.List;

import com.uisrael.consumopsip.model.dto.request.RolRequestDto;
import com.uisrael.consumopsip.model.dto.response.RolResponseDto;

public interface IRolService {

	List<RolResponseDto> listarRoles(String token);

	void guardarRol(RolRequestDto nuevoRol, String token);

	RolResponseDto buscarPorId(int idRol, String token);

	void eliminarRol(int idRol, String token);
}