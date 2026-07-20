package com.uisrael.consumopsip.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uisrael.consumopsip.model.dto.request.MarcacionesRequestDto;
import com.uisrael.consumopsip.model.dto.response.MarcacionesResponseDto;
import com.uisrael.consumopsip.service.IMarcacionesService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/marcaciones")
public class MarcacionesController {

    @Autowired
    private IMarcacionesService servicioMarcaciones;

    @GetMapping
    public String leerPagina(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        List<MarcacionesResponseDto> marcacionesBD = servicioMarcaciones.listarMarcaciones(token);
        model.addAttribute("listamarcaciones", marcacionesBD);
        return "marcaciones/listarmarcaciones";
    }

    @GetMapping("/nuevo")
    public String nuevaMarcacion(HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }
        model.addAttribute("marcacion", new MarcacionesRequestDto());
        return "marcaciones/crearmarcacion";
    }

    @PostMapping("/guardar")
    public String guardarMarcacion(@ModelAttribute MarcacionesRequestDto marcacion, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        servicioMarcaciones.guardarMarcacion(marcacion, token);
        return "redirect:/marcaciones";
    }
}