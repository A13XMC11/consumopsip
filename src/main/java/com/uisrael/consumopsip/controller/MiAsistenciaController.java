package com.uisrael.consumopsip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		if (session.getAttribute("token") == null) {
			return "redirect:/login";
		}
		model.addAttribute("nombre", session.getAttribute("nombre"));
		return "miasistencia/inicio";
	}

	@PostMapping("/solicitar")
	public String solicitar(@RequestParam String tipo, HttpSession session, Model model) {
		String token = (String) session.getAttribute("token");
		if (token == null) {
			return "redirect:/login";
		}
		servicioMarcaciones.solicitarMarcacion(token, tipo);
		model.addAttribute("nombre", session.getAttribute("nombre"));
		model.addAttribute("mensaje", "Revisa tu correo para confirmar tu registro.");
		return "miasistencia/inicio";
	}
}
