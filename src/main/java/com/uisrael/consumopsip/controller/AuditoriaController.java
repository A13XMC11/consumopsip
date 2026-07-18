package com.uisrael.consumopsip.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uisrael.consumopsip.model.dto.request.AuditoriaRequestDto;
import com.uisrael.consumopsip.model.dto.response.AuditoriaResponseDto;
import com.uisrael.consumopsip.service.IAuditoriaService;

@Controller
@RequestMapping("/auditoria")
public class AuditoriaController {

    @Autowired
    private IAuditoriaService servicioAuditoria;

    @GetMapping
    public String leerPagina(Model model) {
        List<AuditoriaResponseDto> auditoriasBD = servicioAuditoria.listarAuditorias();
        model.addAttribute("listaauditorias", auditoriasBD);
        return "auditoria/listarauditoria";
    }

    @GetMapping("/nuevo")
    public String nuevaAuditoria(Model model) {
        model.addAttribute("auditoria", new AuditoriaRequestDto());
        return "auditoria/crearauditoria";
    }

    @PostMapping("/guardar")
    public String guardarAuditoria(@ModelAttribute AuditoriaRequestDto auditoria) {
        servicioAuditoria.guardarAuditoria(auditoria);
        return "redirect:/auditoria";
    }
}