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
import com.uisrael.consumopsip.service.IEmpleadoHorarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/empleadohorario")
public class EmpleadoHorarioController {

    @Autowired
    private IEmpleadoHorarioService servicioEmpleadoHorario;

    @GetMapping
    public String leerPagina(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        List<EmpleadoHorarioResponseDto> empleadoHorariosBD = servicioEmpleadoHorario.listarEmpleadoHorarios(token);
        model.addAttribute("listaempleadohorarios", empleadoHorariosBD);
        return "empleadohorario/listarempleadohorario";
    }

    @GetMapping("/nuevo")
    public String nuevoEmpleadoHorario(HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }
        model.addAttribute("empleadohorario", new EmpleadoHorarioRequestDto());
        return "empleadohorario/crearempleadohorario";
    }

    @PostMapping("/guardar")
    public String guardarEmpleadoHorario(@ModelAttribute EmpleadoHorarioRequestDto empleadoHorario, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        servicioEmpleadoHorario.guardarEmpleadoHorario(empleadoHorario, token);
        return "redirect:/empleadohorario";
    }
}