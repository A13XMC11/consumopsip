package com.uisrael.consumopsip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.uisrael.consumopsip.model.dto.response.MarcacionesResponseDto;
import com.uisrael.consumopsip.service.IMarcacionesService;

@Controller
@RequestMapping("/asistencia")
public class RegistroAsistenciaController {

	private final IMarcacionesService servicioMarcaciones;

	public RegistroAsistenciaController(IMarcacionesService servicioMarcaciones) {
		this.servicioMarcaciones = servicioMarcaciones;
	}

	@GetMapping("/registrar")
	public String paginaCaptura(@RequestParam String token, Model model) {
		model.addAttribute("token", token);
		return "asistencia/registrar";
	}

	@PostMapping("/confirmar")
	public String confirmar(@RequestParam String token, @RequestParam double lat, @RequestParam double lng, Model model) {
		try {
			MarcacionesResponseDto resultado = servicioMarcaciones.registrarMarcacion(token, lat, lng);
			model.addAttribute("resultado", resultado);
		} catch (WebClientResponseException e) {
			model.addAttribute("error", extraerMensajeError(e));
		} catch (Exception e) {
			model.addAttribute("error", "No se pudo registrar la marcación. El link puede haber expirado o ya fue usado.");
		}
		return "asistencia/resultado";
	}

	private String extraerMensajeError(WebClientResponseException e) {
		String cuerpo = e.getResponseBodyAsString();
		java.util.regex.Matcher matcher = java.util.regex.Pattern
				.compile("\"message\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"").matcher(cuerpo);
		if (matcher.find()) {
			return matcher.group(1).replace("\\\"", "\"");
		}
		return "No se pudo registrar la marcación. El link puede haber expirado o ya fue usado.";
	}
}
