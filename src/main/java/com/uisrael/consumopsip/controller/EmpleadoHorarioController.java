package com.uisrael.consumopsip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.uisrael.consumopsip.model.dto.request.EmpleadoHorarioRequestDto;
import com.uisrael.consumopsip.model.dto.response.EmpleadoHorarioResponseDto;
import com.uisrael.consumopsip.model.dto.response.EmpleadoResponseDto;
import com.uisrael.consumopsip.model.dto.response.HorariosResponseDto;
import com.uisrael.consumopsip.service.IEmpleadoHorarioService;
import com.uisrael.consumopsip.service.IEmpleadoService;
import com.uisrael.consumopsip.service.IHorariosService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/empleadohorario")
public class EmpleadoHorarioController {
	
    @Autowired
    private IEmpleadoService servicioEmpleado;

    @Autowired
    private IHorariosService servicioHorarios;
    
    @Autowired
    private IEmpleadoHorarioService servicioEmpleadoHorario;

    @GetMapping
    public String leerPagina(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        List<EmpleadoHorarioResponseDto> empleadoHorariosBD = servicioEmpleadoHorario.listarEmpleadoHorarios(token);
        model.addAttribute("listaempleadohorarios", empleadoHorariosBD);
        
        List<EmpleadoResponseDto> empleadosBD = servicioEmpleado.listarEmpleados(token);
        Map<Integer, String> mapaEmpleados = new HashMap<>();
        for (EmpleadoResponseDto e : empleadosBD) {
            mapaEmpleados.put(e.getIdEmpleado(), e.getNombreEmpleado() + " " + e.getApellidosEmpleado());
        }
        model.addAttribute("mapaEmpleados", mapaEmpleados);

        List<HorariosResponseDto> horariosBD = servicioHorarios.listarHorarios(token);
        Map<Integer, String> mapaHorarios = new HashMap<>();
        for (HorariosResponseDto h : horariosBD) {
            mapaHorarios.put(h.getIdHorario(), h.getNombre());
        }
        model.addAttribute("mapaHorarios", mapaHorarios);
        return "empleadohorario/listarempleadohorario";
    }

    @GetMapping("/nuevo")
    public String nuevoEmpleadoHorario(HttpSession session, Model model) {
    	String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        List<EmpleadoResponseDto> empleadosBD = servicioEmpleado.listarEmpleados(token);
        List<HorariosResponseDto> horariosBD = servicioHorarios.listarHorarios(token);
        model.addAttribute("listaempleados", empleadosBD);
        model.addAttribute("listahorarios", horariosBD);
        model.addAttribute("empleadohorario", new EmpleadoHorarioRequestDto());
        return "empleadohorario/crearempleadohorario";
    }

    @PostMapping("/guardar")
    public String guardarEmpleadoHorario(@ModelAttribute EmpleadoHorarioRequestDto empleadoHorario, HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        try {
            servicioEmpleadoHorario.guardarEmpleadoHorario(empleadoHorario, token);
        } catch (WebClientResponseException e) {
            model.addAttribute("listaempleados", servicioEmpleado.listarEmpleados(token));
            model.addAttribute("listahorarios", servicioHorarios.listarHorarios(token));
            model.addAttribute("empleadohorario", empleadoHorario);
            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                model.addAttribute("conflictoHorario", true);
                model.addAttribute("conflictoMensaje", extraerMensajeError(e));
            } else {
                model.addAttribute("error", "No se pudo guardar la asignación: la fecha de fin no puede ser anterior a la fecha de inicio.");
            }
            return "empleadohorario/crearempleadohorario";
        }
        return "redirect:/empleadohorario";
    }

    private String extraerMensajeError(WebClientResponseException e) {
        String cuerpo = e.getResponseBodyAsString();
        java.util.regex.Matcher matcher = java.util.regex.Pattern
                .compile("\"message\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"").matcher(cuerpo);
        if (matcher.find()) {
            return matcher.group(1).replace("\\\"", "\"");
        }
        return "El empleado ya tiene un horario activo asignado.";
    }

    @GetMapping("/editar/{idAsignacion}")
    public String editarEmpleadoHorario(@PathVariable int idAsignacion, HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        EmpleadoHorarioResponseDto asignacion = servicioEmpleadoHorario.buscarPorId(idAsignacion, token);
        if (asignacion == null) {
            return "redirect:/empleadohorario";
        }
        EmpleadoHorarioRequestDto dto = new EmpleadoHorarioRequestDto();
        dto.setIdAsignacion(asignacion.getIdAsignacion());
        dto.setIdEmpleado(asignacion.getIdEmpleado());
        dto.setIdHorario(asignacion.getIdHorario());
        dto.setFechaInicio(asignacion.getFechaInicio());
        dto.setFechaFin(asignacion.getFechaFin());
        dto.setEstadoEmpleadoHorario(asignacion.isEstadoEmpleadoHorario());

        List<EmpleadoResponseDto> empleadosBD = servicioEmpleado.listarEmpleados(token);
        List<HorariosResponseDto> horariosBD = servicioHorarios.listarHorarios(token);
        model.addAttribute("listaempleados", empleadosBD);
        model.addAttribute("listahorarios", horariosBD);
        model.addAttribute("empleadohorario", dto);
        return "empleadohorario/crearempleadohorario";
    }

    @GetMapping("/eliminar/{idAsignacion}")
    public String eliminarEmpleadoHorario(@PathVariable int idAsignacion, HttpSession session, RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/login";
        }
        if (!"ADMIN".equals(session.getAttribute("rol"))) {
            return "redirect:/miasistencia";
        }
        try {
            servicioEmpleadoHorario.eliminarEmpleadoHorario(idAsignacion, token);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar la asignación.");
        }
        return "redirect:/empleadohorario";
    }
}