package com.uisrael.consumopsip.service;

import java.util.List;

import com.uisrael.consumopsip.model.dto.request.AuditoriaRequestDto;
import com.uisrael.consumopsip.model.dto.response.AuditoriaResponseDto;

public interface IAuditoriaService {

	List<AuditoriaResponseDto> listarAuditorias(String token);

	void guardarAuditoria(AuditoriaRequestDto nuevaAuditoria, String token);
}