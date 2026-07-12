package com.uisrael.consumopsip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empleadohorario")
public class EmpleadoHorarioController {
	@GetMapping
	public String leerPagina() {
		return "empleadohorario/listarempleadohorario";
	}
}
