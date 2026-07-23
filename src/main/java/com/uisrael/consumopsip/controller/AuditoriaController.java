package com.uisrael.consumopsip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uisrael.consumopsip.model.dto.request.AuditoriaRequestDto;
import com.uisrael.consumopsip.model.dto.response.AuditoriaResponseDto;
import com.uisrael.consumopsip.model.dto.response.EmpleadoResponseDto;
import com.uisrael.consumopsip.service.IAuditoriaService;
import com.uisrael.consumopsip.service.IEmpleadoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auditoria")
public class AuditoriaController {

	@Autowired
	private IAuditoriaService servicioAuditoria;
	
	@Autowired
	private IEmpleadoService servicioEmpleado;

	@GetMapping
	public String leerPagina(HttpSession session, Model model) {
		String token = (String) session.getAttribute("token");
		if (token == null) {
			return "redirect:/login";
		}
		if (!"ADMIN".equals(session.getAttribute("rol"))) {
			return "redirect:/miasistencia";
		}
		List<AuditoriaResponseDto> auditoriasBD = servicioAuditoria.listarAuditorias(token);
		model.addAttribute("listaauditorias", auditoriasBD);
		
		List<EmpleadoResponseDto> empleadosBD = servicioEmpleado.listarEmpleados(token);
		Map<Integer, String> mapaEmpleados = new HashMap<>();
		for (EmpleadoResponseDto e : empleadosBD) {
			mapaEmpleados.put(e.getIdEmpleado(), e.getNombreEmpleado() + " " + e.getApellidosEmpleado());
		}
		model.addAttribute("mapaEmpleados", mapaEmpleados);

		
		return "auditoria/listarauditoria";
	}

	@GetMapping("/nuevo")
	public String nuevaAuditoria(HttpSession session, Model model) {
		if (session.getAttribute("token") == null) {
			return "redirect:/login";
		}
		if (!"ADMIN".equals(session.getAttribute("rol"))) {
			return "redirect:/miasistencia";
		}
		model.addAttribute("auditoria", new AuditoriaRequestDto());
		return "auditoria/crearauditoria";
	}

	@PostMapping("/guardar")
	public String guardarAuditoria(@ModelAttribute AuditoriaRequestDto auditoria, HttpSession session) {
		String token = (String) session.getAttribute("token");
		if (token == null) {
			return "redirect:/login";
		}
		servicioAuditoria.guardarAuditoria(auditoria, token);
		return "redirect:/auditoria";
	}
}