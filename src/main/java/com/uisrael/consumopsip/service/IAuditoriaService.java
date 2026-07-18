package com.uisrael.consumopsip.service;

import java.util.List;
import com.uisrael.consumopsip.model.dto.request.AuditoriaRequestDto;
import com.uisrael.consumopsip.model.dto.response.AuditoriaResponseDto;

public interface IAuditoriaService {
	List<AuditoriaResponseDto> listarAuditorias();

	void guardarAuditoria(AuditoriaRequestDto nuevaAuditoria);
}