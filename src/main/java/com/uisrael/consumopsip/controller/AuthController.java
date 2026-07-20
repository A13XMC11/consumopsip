package com.uisrael.consumopsip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uisrael.consumopsip.model.dto.response.LoginResponseDto;
import com.uisrael.consumopsip.service.IAuthService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

	private final IAuthService servicioAuth;

	public AuthController(IAuthService servicioAuth) {
		this.servicioAuth = servicioAuth;
	}

	@GetMapping("/login")
	public String mostrarLogin() {
		return "login/login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String correo, @RequestParam String contrasena, HttpSession session,
			Model model) {
		LoginResponseDto respuesta = servicioAuth.login(correo, contrasena);

		if (respuesta == null) {
			model.addAttribute("error", "Correo o contraseña incorrectos");
			return "login/login";
		}

		session.setAttribute("token", respuesta.getToken());
		session.setAttribute("idEmpleado", respuesta.getIdEmpleado());
		session.setAttribute("nombre", respuesta.getNombre());
		session.setAttribute("rol", respuesta.getRol());

		return "redirect:/miasistencia";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
