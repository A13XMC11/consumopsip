package com.uisrael.consumopsip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uisrael.consumopsip.model.dto.request.MarcacionesRequestDto;
import com.uisrael.consumopsip.model.dto.response.EmpleadoResponseDto;
import com.uisrael.consumopsip.model.dto.response.MarcacionesResponseDto;
import com.uisrael.consumopsip.service.IEmpleadoService;
import com.uisrael.consumopsip.service.IMarcacionesService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/marcaciones")
public class MarcacionesController {

    @Autowired
    private IMarcacionesService servicioMarcaciones;
    
    @Autowired
    private IEmpleadoService servicioEmpleado;

    @GetMapping
    public String leerPagina(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        List<MarcacionesResponseDto> marcacionesBD = servicioMarcaciones.listarMarcaciones(token);
        model.addAttribute("listamarcaciones", marcacionesBD);
        
        List<EmpleadoResponseDto> empleadosBD = servicioEmpleado.listarEmpleados(token);
        Map<Integer, String> mapaEmpleados = new HashMap<>();
        for (EmpleadoResponseDto e : empleadosBD) {
            mapaEmpleados.put(e.getIdEmpleado(), e.getNombreEmpleado() + " " + e.getApellidosEmpleado());
        }
        model.addAttribute("mapaEmpleados", mapaEmpleados);

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