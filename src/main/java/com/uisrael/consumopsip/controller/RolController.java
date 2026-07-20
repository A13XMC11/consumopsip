package com.uisrael.consumopsip.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uisrael.consumopsip.model.dto.request.RolRequestDto;
import com.uisrael.consumopsip.model.dto.response.RolResponseDto;
import com.uisrael.consumopsip.service.IRolService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/rol")
public class RolController {

	@Autowired
	private IRolService servicioRol;

	@GetMapping
	public String leerPagina(HttpSession session, Model model) {
		String token = (String) session.getAttribute("token");
		if (token == null) {
			return "redirect:/login";
		}
		List<RolResponseDto> rolesBD = servicioRol.listarRoles(token);
		model.addAttribute("listaroles", rolesBD);
		return "rol/listarrol";
	}

	@GetMapping("/nuevo")
	public String nuevoRol(HttpSession session, Model model) {
		if (session.getAttribute("token") == null) {
			return "redirect:/login";
		}
		model.addAttribute("rol", new RolRequestDto());
		return "rol/crearrol";
	}

	@PostMapping("/guardar")
	public String guardarRol(@ModelAttribute RolRequestDto rol, HttpSession session) {
		String token = (String) session.getAttribute("token");
		if (token == null) {
			return "redirect:/login";
		}
		servicioRol.guardarRol(rol, token);
		return "redirect:/rol";
	}
}
