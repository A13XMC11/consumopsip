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

import com.uisrael.consumopsip.model.dto.request.EmpleadoRequestDto;
import com.uisrael.consumopsip.model.dto.response.EmpleadoResponseDto;
import com.uisrael.consumopsip.model.dto.response.RolResponseDto;
import com.uisrael.consumopsip.service.IEmpleadoService;
import com.uisrael.consumopsip.service.IRolService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {

	@Autowired
	private IEmpleadoService servicioEmpleado;

	@Autowired
	private IRolService servicioRol;

	private boolean noEsAdminNiSupervisor(HttpSession session) {
		String rol = (String) session.getAttribute("rol");
		return !"ADMIN".equals(rol) && !"SUPERVISOR".equals(rol);
	}

	private boolean noEsAdmin(HttpSession session) {
		return !"ADMIN".equals(session.getAttribute("rol"));
	}

	@GetMapping
	public String leerPagina(HttpSession session, Model model) {
		String token = (String) session.getAttribute("token");
		if (token == null) {
			return "redirect:/login";
		}
		if (noEsAdminNiSupervisor(session)) {
			return "redirect:/miasistencia";
		}
		List<EmpleadoResponseDto> empleadosBD = servicioEmpleado.listarEmpleados(token);
		model.addAttribute("listaempleados", empleadosBD);
		return "empleado/listarempleado";
	}

	@GetMapping("/nuevo")
	public String nuevoEmpleado(HttpSession session, Model model) {
		String token = (String) session.getAttribute("token");
		if (token == null) {
			return "redirect:/login";
		}
		if (noEsAdmin(session)) {
			return "redirect:/empleado";
		}
		List<RolResponseDto> rolesBD = servicioRol.listarRoles(token);
		model.addAttribute("listaroles", rolesBD);
		model.addAttribute("empleado", new EmpleadoRequestDto());
		return "empleado/crearempleado";
	}

	@PostMapping("/guardar")
	public String guardarEmpleado(@ModelAttribute EmpleadoRequestDto empleado, HttpSession session, Model model) {
		String token = (String) session.getAttribute("token");
		if (token == null) {
			return "redirect:/login";
		}
		if (noEsAdminNiSupervisor(session)) {
			return "redirect:/miasistencia";
		}
		if (empleado.getIdEmpleado() == 0 && noEsAdmin(session)) {
			return "redirect:/empleado";
		}

		try {
			servicioEmpleado.guardarEmpleado(empleado, token);
		} catch (Exception e) {
			List<RolResponseDto> rolesBD = servicioRol.listarRoles(token);
			model.addAttribute("listaroles", rolesBD);
			model.addAttribute("empleado", empleado);
			model.addAttribute("error", "No se pudo guardar el empleado: revisa el número de documento ingresado.");
			return "empleado/crearempleado";
		}
		return "redirect:/empleado";
	}

	@GetMapping("/editar/{idEmpleado}")
	public String editarEmpleado(@PathVariable int idEmpleado, HttpSession session, Model model) {
		String token = (String) session.getAttribute("token");
		if (token == null) {
			return "redirect:/login";
		}
		if (noEsAdminNiSupervisor(session)) {
			return "redirect:/miasistencia";
		}
		EmpleadoResponseDto empleado = servicioEmpleado.buscarPorId(idEmpleado, token);
		if (empleado == null) {
			return "redirect:/empleado";
		}
		EmpleadoRequestDto dto = new EmpleadoRequestDto();
		dto.setIdEmpleado(empleado.getIdEmpleado());
		dto.setIdRol(empleado.getIdRol());
		dto.setNombreEmpleado(empleado.getNombreEmpleado());
		dto.setApellidosEmpleado(empleado.getApellidosEmpleado());
		dto.setCorreoEmpleado(empleado.getCorreoEmpleado());
		dto.setTipoDocumento(empleado.getTipoDocumento());
		dto.setNumeroDocumento(empleado.getNumeroDocumento());
		dto.setEstadoEmpleado(empleado.isEstadoEmpleado());

		List<RolResponseDto> rolesBD = servicioRol.listarRoles(token);
		model.addAttribute("listaroles", rolesBD);
		model.addAttribute("empleado", dto);
		return "empleado/crearempleado";
	}

	@GetMapping("/desactivar/{idEmpleado}")
	public String desactivarEmpleado(@PathVariable int idEmpleado, HttpSession session, RedirectAttributes redirectAttributes) {
		String token = (String) session.getAttribute("token");
		if (token == null) {
			return "redirect:/login";
		}
		if (noEsAdmin(session)) {
			return "redirect:/empleado";
		}
		try {
			servicioEmpleado.desactivarEmpleado(idEmpleado, token);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "No se pudo desactivar el empleado.");
		}
		return "redirect:/empleado";
	}
}