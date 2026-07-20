package com.uisrael.consumopsip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		} catch (Exception e) {
			model.addAttribute("error", "No se pudo registrar la marcación. El link puede haber expirado o ya fue usado.");
		}
		return "asistencia/resultado";
	}
}
