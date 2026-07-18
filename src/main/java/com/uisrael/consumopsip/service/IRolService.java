package com.uisrael.consumopsip.service;

import java.util.List;
import com.uisrael.consumopsip.model.dto.request.RolRequestDto;
import com.uisrael.consumopsip.model.dto.response.RolResponseDto;

public interface IRolService {
	List<RolResponseDto> listarRoles();

	void guardarRol(RolRequestDto nuevoRol);
}