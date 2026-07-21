package com.uisrael.consumopsip.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uisrael.consumopsip.model.dto.request.HorariosRequestDto;
import com.uisrael.consumopsip.model.dto.response.HorariosResponseDto;
import com.uisrael.consumopsip.service.IHorariosService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/horarios")
public class HorariosController {

    @Autowired
    private IHorariosService servicioHorarios;

    @GetMapping
    public String leerPagina(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        List<HorariosResponseDto> horariosBD = servicioHorarios.listarHorarios(token);
        model.addAttribute("listahorarios", horariosBD);
        return "horarios/listarhorarios";
    }

    @GetMapping("/nuevo")
    public String nuevoHorario(HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        model.addAttribute("horario", new HorariosRequestDto());
        return "horarios/crearhorario";
    }

    @PostMapping("/guardar")
    public String guardarHorario(@ModelAttribute HorariosRequestDto horario, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        servicioHorarios.guardarHorario(horario, token);
        return "redirect:/horarios";
    }
}