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
import com.uisrael.consumopsip.model.dto.request.CodigosTemporalesRequestDto;
import com.uisrael.consumopsip.model.dto.response.CodigosTemporalesResponseDto;
import com.uisrael.consumopsip.model.dto.response.EmpleadoResponseDto;
import com.uisrael.consumopsip.service.ICodigosTemporalesService;
import com.uisrael.consumopsip.service.IEmpleadoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/codigostemporales")
public class CodigosTemporalesController {

    @Autowired
    private ICodigosTemporalesService servicioCodigosTemporales;
    
    @Autowired
    private IEmpleadoService servicioEmpleado;

    @GetMapping
    public String leerPagina(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        List<CodigosTemporalesResponseDto> codigosBD = servicioCodigosTemporales.listarCodigosTemporales(token);
        model.addAttribute("listacodigos", codigosBD);

        List<EmpleadoResponseDto> empleadosBD = servicioEmpleado.listarEmpleados(token);
        Map<Integer, String> mapaEmpleados = new HashMap<>();
        for (EmpleadoResponseDto e : empleadosBD) {
            mapaEmpleados.put(e.getIdEmpleado(), e.getNombreEmpleado() + " " + e.getApellidosEmpleado());
        }
        model.addAttribute("mapaEmpleados", mapaEmpleados);
        return "codigostemporales/listarcodigostemporales";
    }

    @GetMapping("/nuevo")
    public String nuevoCodigo(HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        model.addAttribute("codigo", new CodigosTemporalesRequestDto());
        return "codigostemporales/crearcodigotemporal";
    }

    @PostMapping("/guardar")
    public String guardarCodigo(@ModelAttribute CodigosTemporalesRequestDto codigo, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        servicioCodigosTemporales.guardarCodigoTemporal(codigo, token);
        return "redirect:/codigostemporales";
    }
}