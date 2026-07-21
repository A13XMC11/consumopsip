package com.uisrael.consumopsip.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uisrael.consumopsip.model.dto.request.EmpleadoHorarioRequestDto;
import com.uisrael.consumopsip.model.dto.response.EmpleadoHorarioResponseDto;
import com.uisrael.consumopsip.model.dto.response.EmpleadoResponseDto;
import com.uisrael.consumopsip.model.dto.response.HorariosResponseDto;
import com.uisrael.consumopsip.service.IEmpleadoHorarioService;
import com.uisrael.consumopsip.service.IEmpleadoService;
import com.uisrael.consumopsip.service.IHorariosService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/empleadohorario")
public class EmpleadoHorarioController {
	
    @Autowired
    private IEmpleadoService servicioEmpleado;

    @Autowired
    private IHorariosService servicioHorarios;
    
    @Autowired
    private IEmpleadoHorarioService servicioEmpleadoHorario;

    @GetMapping
    public String leerPagina(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        List<EmpleadoHorarioResponseDto> empleadoHorariosBD = servicioEmpleadoHorario.listarEmpleadoHorarios(token);
        model.addAttribute("listaempleadohorarios", empleadoHorariosBD);
        return "empleadohorario/listarempleadohorario";
    }

    @GetMapping("/nuevo")
    public String nuevoEmpleadoHorario(HttpSession session, Model model) {
    	String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        List<EmpleadoResponseDto> empleadosBD = servicioEmpleado.listarEmpleados(token);
        List<HorariosResponseDto> horariosBD = servicioHorarios.listarHorarios(token);
        model.addAttribute("listaempleados", empleadosBD);
        model.addAttribute("listahorarios", horariosBD);
        model.addAttribute("empleadohorario", new EmpleadoHorarioRequestDto());
        return "empleadohorario/crearempleadohorario";
    }

    @PostMapping("/guardar")
    public String guardarEmpleadoHorario(@ModelAttribute EmpleadoHorarioRequestDto empleadoHorario, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        servicioEmpleadoHorario.guardarEmpleadoHorario(empleadoHorario, token);
        return "redirect:/empleadohorario";
    }
}