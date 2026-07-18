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

@Controller
@RequestMapping("/marcaciones")
public class MarcacionesController {

    @Autowired
    private IMarcacionesService servicioMarcaciones;

    @GetMapping
    public String leerPagina(Model model) {
        List<MarcacionesResponseDto> marcacionesBD = servicioMarcaciones.listarMarcaciones();
        model.addAttribute("listamarcaciones", marcacionesBD);
        return "marcaciones/listarmarcaciones";
    }

    @GetMapping("/nuevo")
    public String nuevaMarcacion(Model model) {
        model.addAttribute("marcacion", new MarcacionesRequestDto());
        return "marcaciones/crearmarcacion";
    }

    @PostMapping("/guardar")
    public String guardarMarcacion(@ModelAttribute MarcacionesRequestDto marcacion) {
        servicioMarcaciones.guardarMarcacion(marcacion);
        return "redirect:/marcaciones";
    }
}