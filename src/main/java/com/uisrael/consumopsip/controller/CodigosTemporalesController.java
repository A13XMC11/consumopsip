package com.uisrael.consumopsip.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uisrael.consumopsip.model.dto.request.CodigosTemporalesRequestDto;
import com.uisrael.consumopsip.model.dto.response.CodigosTemporalesResponseDto;
import com.uisrael.consumopsip.service.ICodigosTemporalesService;

@Controller
@RequestMapping("/codigostemporales")
public class CodigosTemporalesController {

    @Autowired
    private ICodigosTemporalesService servicioCodigosTemporales;

    @GetMapping
    public String leerPagina(Model model) {
        List<CodigosTemporalesResponseDto> codigosBD = servicioCodigosTemporales.listarCodigosTemporales();
        model.addAttribute("listacodigos", codigosBD);
        return "codigostemporales/listarcodigostemporales";
    }

    @GetMapping("/nuevo")
    public String nuevoCodigo(Model model) {
        model.addAttribute("codigo", new CodigosTemporalesRequestDto());
        return "codigostemporales/crearcodigotemporal";
    }

    @PostMapping("/guardar")
    public String guardarCodigo(@ModelAttribute CodigosTemporalesRequestDto codigo) {
        servicioCodigosTemporales.guardarCodigoTemporal(codigo);
        return "redirect:/codigostemporales";
    }
}