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

@Controller
@RequestMapping("/horarios")
public class HorariosController {

    @Autowired
    private IHorariosService servicioHorarios;

    @GetMapping
    public String leerPagina(Model model) {
        List<HorariosResponseDto> horariosBD = servicioHorarios.listarHorarios();
        model.addAttribute("listahorarios", horariosBD);
        return "horarios/listarhorarios";
    }

    @GetMapping("/nuevo")
    public String nuevoHorario(Model model) {
        model.addAttribute("horario", new HorariosRequestDto());
        return "horarios/crearhorario";
    }

    @PostMapping("/guardar")
    public String guardarHorario(@ModelAttribute HorariosRequestDto horario) {
        servicioHorarios.guardarHorario(horario);
        return "redirect:/horarios";
    }
}