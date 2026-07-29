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
	
	@GetMapping("/")
	public String raiz(HttpSession session) {
		if (session.getAttribute("token") != null) {
			return "redirect:/miasistencia";
		}
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String mostrarLogin() {
		return "login/login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String correo, @RequestParam String contrasena, HttpSession session,
			Model model) {
		LoginResponseDto respuesta;
		try {
			respuesta = servicioAuth.login(correo, contrasena);
		} catch (RuntimeException e) {
			model.addAttribute("error", e.getMessage());
			return "login/login";
		}

		if (respuesta == null) {
			model.addAttribute("error", "Correo o contraseña incorrectos");
			return "login/login";
		}

		session.setAttribute("token", respuesta.getToken());
		session.setAttribute("idEmpleado", respuesta.getIdEmpleado());
		session.setAttribute("nombre", respuesta.getNombre());
		session.setAttribute("rol", respuesta.getRol());
		session.setAttribute("debeCambiarContrasena", respuesta.isDebeCambiarContrasena());

		if (respuesta.isDebeCambiarContrasena()) {
			return "redirect:/cambiar-contrasena";
		}
		return "redirect:/miasistencia";
	}

	@GetMapping("/cambiar-contrasena")
	public String mostrarCambiarContrasena(HttpSession session) {
		if (session.getAttribute("token") == null) {
			return "redirect:/login";
		}
		return "login/cambiarcontrasena";
	}

	@PostMapping("/cambiar-contrasena")
	public String cambiarContrasena(@RequestParam String contrasenaActual, @RequestParam String contrasenaNueva,
			@RequestParam String confirmarContrasena, HttpSession session, Model model) {
		String token = (String) session.getAttribute("token");
		if (token == null) {
			return "redirect:/login";
		}

		if (!contrasenaNueva.equals(confirmarContrasena)) {
			model.addAttribute("error", "Las contraseñas no coinciden");
			return "login/cambiarcontrasena";
		}

		try {
			servicioAuth.cambiarContrasena(token, contrasenaActual, contrasenaNueva);
		} catch (Exception e) {
			model.addAttribute("error", "La contraseña actual no es correcta");
			return "login/cambiarcontrasena";
		}

		session.setAttribute("debeCambiarContrasena", false);
		return "redirect:/miasistencia";
	}

	@GetMapping("/verificar-correo")
	public String verificarCorreo(@RequestParam String token, Model model) {
		try {
			servicioAuth.verificarCorreo(token);
			model.addAttribute("mensaje", "Tu correo fue verificado correctamente. Ya puedes iniciar sesión.");
		} catch (Exception e) {
			model.addAttribute("error", "El link de verificación no es válido o expiró.");
		}
		return "login/verificarcorreo";
	}

	@GetMapping("/olvide-contrasena")
	public String mostrarOlvideContrasena() {
		return "login/olvidecontrasena";
	}

	@PostMapping("/olvide-contrasena")
	public String olvideContrasena(@RequestParam String correo, Model model) {
		try {
			servicioAuth.solicitarRecuperacionContrasena(correo);
		} catch (Exception e) {
			// No se revela si el correo existe o no para evitar enumeración de usuarios
		}
		model.addAttribute("mensaje",
				"Si el correo está registrado, te enviamos un link para restablecer tu contraseña.");
		return "login/olvidecontrasena";
	}

	@GetMapping("/restablecer-contrasena")
	public String mostrarRestablecerContrasena(@RequestParam String token, Model model) {
		model.addAttribute("token", token);
		return "login/restablecercontrasena";
	}

	@PostMapping("/restablecer-contrasena")
	public String restablecerContrasena(@RequestParam String token, @RequestParam String contrasenaNueva,
			@RequestParam String confirmarContrasena, Model model) {
		if (!contrasenaNueva.equals(confirmarContrasena)) {
			model.addAttribute("token", token);
			model.addAttribute("error", "Las contraseñas no coinciden");
			return "login/restablecercontrasena";
		}

		try {
			servicioAuth.restablecerContrasena(token, contrasenaNueva);
		} catch (Exception e) {
			model.addAttribute("token", token);
			model.addAttribute("error", "El link de recuperación no es válido o expiró.");
			return "login/restablecercontrasena";
		}

		model.addAttribute("mensaje", "Tu contraseña fue restablecida correctamente. Ya puedes iniciar sesión.");
		return "login/login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
