package com.uisrael.consumopsip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    	if (noEsAdminNiSupervisor(session)) {
            return "redirect:/miasistencia";
        }
    	List<RolResponseDto> rolesBD = servicioRol.listarRoles(token);
        model.addAttribute("listaroles", rolesBD);
        model.addAttribute("empleado", new EmpleadoRequestDto());
        return "empleado/crearempleado";
    }

    @PostMapping("/guardar")
    public String guardarEmpleado(@ModelAttribute EmpleadoRequestDto empleado, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        if (noEsAdminNiSupervisor(session)) {
            return "redirect:/miasistencia";
        }
        servicioEmpleado.guardarEmpleado(empleado, token);
        return "redirect:/empleado";
    }
}