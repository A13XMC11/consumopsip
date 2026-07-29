package com.uisrael.consumopsip.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

		if (!"ADMIN".equals(session.getAttribute("rol"))) {
			return "redirect:/miasistencia";
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

		if (!"ADMIN".equals(session.getAttribute("rol"))) {
			return "redirect:/miasistencia";
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

		if (!"ADMIN".equals(session.getAttribute("rol"))) {
			return "redirect:/miasistencia";
		}

		servicioRol.guardarRol(rol, token);
		return "redirect:/rol";
	}

	@GetMapping("/editar/{idRol}")
	public String editarRol(@PathVariable int idRol, HttpSession session, Model model) {
		if (session.getAttribute("token") == null) {
			return "redirect:/login";
		}
		if (!"ADMIN".equals(session.getAttribute("rol"))) {
			return "redirect:/miasistencia";
		}
		String token = (String) session.getAttribute("token");
		RolResponseDto rol = servicioRol.buscarPorId(idRol, token);
		if (rol == null) {
			return "redirect:/rol";
		}
		RolRequestDto dto = new RolRequestDto();
		dto.setIdRol(rol.getIdRol());
		dto.setNombreRol(rol.getNombreRol());
		dto.setDescripcionRol(rol.getDescripcionRol());
		model.addAttribute("rol", dto);
		model.addAttribute("creadoRol", rol.getCreadoRol());
		return "rol/crearrol";
	}

	@GetMapping("/eliminar/{idRol}")
	public String eliminarRol(@PathVariable int idRol, HttpSession session, RedirectAttributes redirectAttributes) {
		String token = (String) session.getAttribute("token");
		if (token == null) {
			return "redirect:/login";
		}
		if (!"ADMIN".equals(session.getAttribute("rol"))) {
			return "redirect:/miasistencia";
		}
		try {
			servicioRol.eliminarRol(idRol, token);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error",
					"No se pudo eliminar el rol: probablemente está asignado a uno o más empleados.");
		}
		return "redirect:/rol";
	}
}
