package com.uisrael.consumopsip.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uisrael.consumopsip.model.dto.request.RolRequestDto;
import com.uisrael.consumopsip.model.dto.response.RolResponseDto;
import com.uisrael.consumopsip.service.IRolService;

@Controller
@RequestMapping("/rol")
public class RolController {

    @Autowired
    private IRolService servicioRol;

    @GetMapping
    public String leerPagina(Model model) {
        List<RolResponseDto> rolesBD = servicioRol.listarRoles();
        model.addAttribute("listaroles", rolesBD);
        return "rol/listarrol";
    }

    @GetMapping("/nuevo")
    public String nuevoRol(Model model) {
        model.addAttribute("rol", new RolRequestDto());
        return "rol/crearrol";
    }

    @PostMapping("/guardar")
    public String guardarRol(@ModelAttribute RolRequestDto rol) {
        servicioRol.guardarRol(rol);
        return "redirect:/rol";
    }
}