package com.uisrael.consumopsip.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uisrael.consumopsip.model.dto.request.UbicacionRequestDto;
import com.uisrael.consumopsip.model.dto.response.UbicacionResponseDto;
import com.uisrael.consumopsip.service.IUbicacionService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ubicacion")
public class UbicacionController {

    @Autowired
    private IUbicacionService servicioUbicacion;

    @GetMapping
    public String leerPagina(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        List<UbicacionResponseDto> ubicacionesBD = servicioUbicacion.listarUbicaciones(token);
        model.addAttribute("listaubicaciones", ubicacionesBD);
        return "ubicacion/listarubicacion";
    }

    @GetMapping("/nuevo")
    public String nuevaUbicacion(HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }
        model.addAttribute("ubicacion", new UbicacionRequestDto());
        return "ubicacion/crearubicacion";
    }

    @PostMapping("/guardar")
    public String guardarUbicacion(@ModelAttribute UbicacionRequestDto ubicacion, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        servicioUbicacion.guardarUbicacion(ubicacion, token);
        return "redirect:/ubicacion";
    }
}
