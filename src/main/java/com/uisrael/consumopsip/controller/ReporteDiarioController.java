package com.uisrael.consumopsip.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uisrael.consumopsip.model.dto.request.ReporteDiarioRequestDto;
import com.uisrael.consumopsip.model.dto.response.ReporteDiarioResponseDto;
import com.uisrael.consumopsip.service.IReporteDiarioService;

@Controller
@RequestMapping("/reportediario")
public class ReporteDiarioController {

    @Autowired
    private IReporteDiarioService servicioReporteDiario;

    @GetMapping
    public String leerPagina(Model model) {
        List<ReporteDiarioResponseDto> reportesBD = servicioReporteDiario.listarReportesDiarios();
        model.addAttribute("listareportes", reportesBD);
        return "reportediario/listarreportediario";
    }

    @GetMapping("/nuevo")
    public String nuevoReporte(Model model) {
        model.addAttribute("reporte", new ReporteDiarioRequestDto());
        return "reportediario/crearreportediario";
    }

    @PostMapping("/guardar")
    public String guardarReporte(@ModelAttribute ReporteDiarioRequestDto reporte) {
        servicioReporteDiario.guardarReporteDiario(reporte);
        return "redirect:/reportediario";
    }
}