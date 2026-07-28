package com.uisrael.consumopsip.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
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
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
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
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        servicioUbicacion.guardarUbicacion(ubicacion, token);
        return "redirect:/ubicacion";
    }

    @GetMapping("/editar/{idUbicacion}")
    public String editarUbicacion(@PathVariable int idUbicacion, HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        String token = (String) session.getAttribute("token");
        UbicacionResponseDto ubicacion = servicioUbicacion.buscarPorId(idUbicacion, token);
        if (ubicacion == null) {
            return "redirect:/ubicacion";
        }
        UbicacionRequestDto dto = new UbicacionRequestDto();
        dto.setIdUbicacion(ubicacion.getIdUbicacion());
        dto.setNombreUbicacion(ubicacion.getNombreUbicacion());
        dto.setLatitudUbicacion(ubicacion.getLatitudUbicacion());
        dto.setLongitudUbicacion(ubicacion.getLongitudUbicacion());
        dto.setRadioMetrosUbicacion(ubicacion.getRadioMetrosUbicacion());
        dto.setEstadoUbicacion(ubicacion.isEstadoUbicacion());
        model.addAttribute("ubicacion", dto);
        return "ubicacion/crearubicacion";
    }

    @GetMapping("/desactivar/{idUbicacion}")
    public String desactivarUbicacion(@PathVariable int idUbicacion, HttpSession session, RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        try {
            servicioUbicacion.desactivarUbicacion(idUbicacion, token);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo desactivar la ubicación.");
        }
        return "redirect:/ubicacion";
    }
}
