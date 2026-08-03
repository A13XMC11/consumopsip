package com.uisrael.consumopsip.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.uisrael.consumopsip.model.dto.response.MarcacionesResponseDto;
import com.uisrael.consumopsip.service.IMarcacionesService;


import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/miasistencia")
public class MiAsistenciaController {

	private final IMarcacionesService servicioMarcaciones;

	public MiAsistenciaController(IMarcacionesService servicioMarcaciones) {
		this.servicioMarcaciones = servicioMarcaciones;
	}

	@GetMapping
	public String pantallaPrincipal(HttpSession session, Model model) {
		String token = (String) session.getAttribute("token");
		if (token == null) {
			return "redirect:/login";
		}
		model.addAttribute("nombre", session.getAttribute("nombre"));

		List<MarcacionesResponseDto> marcacionesBD = servicioMarcaciones.listarMarcaciones(token);
		List<MarcacionesResponseDto> marcacionesHoy = marcacionesBD.stream()
				.filter(m -> m.getFechaMarcacion().equals(LocalDate.now())).toList();
		model.addAttribute("marcacionesHoy", marcacionesHoy);

		return "miasistencia/inicio";
	}

	@PostMapping("/solicitar")
	public String solicitar(@RequestParam String tipo, HttpSession session, Model model) {
		String token = (String) session.getAttribute("token");
		if (token == null) {
			return "redirect:/login";
		}
		model.addAttribute("nombre", session.getAttribute("nombre"));
		try {
			servicioMarcaciones.solicitarMarcacion(token, tipo);
			model.addAttribute("mensaje", "Revisa tu correo para confirmar tu registro.");
		} catch (WebClientResponseException e) {
			model.addAttribute("error", "No se pudo registrar tu solicitud: " + extraerMensajeError(e));
		} catch (Exception e) {
			model.addAttribute("error", "No se pudo registrar tu solicitud.");
		}

		List<MarcacionesResponseDto> marcacionesBD = servicioMarcaciones.listarMarcaciones(token);
		List<MarcacionesResponseDto> marcacionesHoy = marcacionesBD.stream()
				.filter(m -> m.getFechaMarcacion().equals(LocalDate.now())).toList();
		model.addAttribute("marcacionesHoy", marcacionesHoy);

		return "miasistencia/inicio";
	}

	private String extraerMensajeError(WebClientResponseException e) {
		String cuerpo = e.getResponseBodyAsString();
		java.util.regex.Matcher matcher = java.util.regex.Pattern
				.compile("\"message\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"").matcher(cuerpo);
		if (matcher.find()) {
			return matcher.group(1).replace("\\\"", "\"");
		}
		return "ocurrió un error inesperado.";
	}
}
