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

    @GetMapping("/editar/{idHorario}")
    public String editarHorario(@PathVariable int idHorario, HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        String token = (String) session.getAttribute("token");
        HorariosResponseDto horario = servicioHorarios.buscarPorId(idHorario, token);
        if (horario == null) {
            return "redirect:/horarios";
        }
        HorariosRequestDto dto = new HorariosRequestDto();
        dto.setIdHorario(horario.getIdHorario());
        dto.setNombre(horario.getNombre());
        dto.setHoraEntrada(horario.getHoraEntrada());
        dto.setHoraSalida(horario.getHoraSalida());
        dto.setToleranciaMinutos(horario.getToleranciaMinutos());
        dto.setEstadoHorario(horario.isEstadoHorario());
        model.addAttribute("horario", dto);
        return "horarios/crearhorario";
    }

    @GetMapping("/desactivar/{idHorario}")
    public String desactivarHorario(@PathVariable int idHorario, HttpSession session, RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        try {
            servicioHorarios.desactivarHorario(idHorario, token);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo desactivar el horario.");
        }
        return "redirect:/horarios";
    }
}