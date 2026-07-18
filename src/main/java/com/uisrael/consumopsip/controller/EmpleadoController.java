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
import com.uisrael.consumopsip.service.IEmpleadoService;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {

    /*@GetMapping
    public String leerPagina() {
        return "empleado/listarempleado";
    }*/
    
    @Autowired
    private IEmpleadoService servicioEmpleado;

    @GetMapping
    public String leerPagina(Model model) {
        List<EmpleadoResponseDto> empleadosBD = servicioEmpleado.listarEmpleados();
        model.addAttribute("listaempleados", empleadosBD);
        return "empleado/listarempleado";
    }

    @GetMapping("/nuevo")
    public String nuevoEmpleado(Model model) {
        model.addAttribute("empleado", new EmpleadoRequestDto());
        return "empleado/crearempleado";
    }

    @PostMapping("/guardar")
    public String guardarEmpleado(@ModelAttribute EmpleadoRequestDto empleado) {
        servicioEmpleado.guardarEmpleado(empleado);
        return "redirect:/empleado";
    }
}