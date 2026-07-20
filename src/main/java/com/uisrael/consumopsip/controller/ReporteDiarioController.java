package com.uisrael.consumopsip.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uisrael.consumopsip.model.dto.request.ReporteDiarioRequestDto;
import com.uisrael.consumopsip.model.dto.response.ReporteDiarioResponseDto;
import com.uisrael.consumopsip.service.IReporteDiarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reportediario")
public class ReporteDiarioController {

	@Autowired
	private IReporteDiarioService servicioReporteDiario;

	@GetMapping
	public String leerPagina(HttpSession session, Model model) {
		String token = (String) session.getAttribute("token");
		if (token == null) {
			return "redirect:/login";
		}
		List<ReporteDiarioResponseDto> reportesBD = servicioReporteDiario.listarReportesDiarios(token);
		model.addAttribute("listareportes", reportesBD);
		return "reportediario/listarreportediario";
	}

	@GetMapping("/nuevo")
	public String nuevoReporte(HttpSession session, Model model) {
		if (session.getAttribute("token") == null) {
			return "redirect:/login";
		}
		model.addAttribute("reporte", new ReporteDiarioRequestDto());
		return "reportediario/crearreportediario";
	}

	@PostMapping("/guardar")
	public String guardarReporte(@ModelAttribute ReporteDiarioRequestDto reporte, HttpSession session) {
		String token = (String) session.getAttribute("token");
		if (token == null) {
			return "redirect:/login";
		}
		servicioReporteDiario.guardarReporteDiario(reporte, token);
		return "redirect:/reportediario";
	}
}