package com.uisrael.consumopsip.service;

import java.util.List;

import com.uisrael.consumopsip.model.dto.request.ReporteDiarioRequestDto;
import com.uisrael.consumopsip.model.dto.response.ReporteDiarioResponseDto;

public interface IReporteDiarioService {

	List<ReporteDiarioResponseDto> listarReportesDiarios(String token);

	void guardarReporteDiario(ReporteDiarioRequestDto nuevoReporte, String token);
}