package com.uisrael.consumopsip.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.uisrael.consumopsip.model.dto.request.CambiarContrasenaRequestDto;
import com.uisrael.consumopsip.model.dto.request.LoginRequestDto;
import com.uisrael.consumopsip.model.dto.request.RecuperarContrasenaRequestDto;
import com.uisrael.consumopsip.model.dto.request.RestablecerContrasenaRequestDto;
import com.uisrael.consumopsip.model.dto.response.LoginResponseDto;
import com.uisrael.consumopsip.service.IAuthService;

@Service
public class AuthServiceImpl implements IAuthService{

	private final WebClient webClient;

	public AuthServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public LoginResponseDto login(String correo, String contrasena) {
		LoginRequestDto request = new LoginRequestDto();
		request.setCorreo(correo);
		request.setContrasena(contrasena);

		try {
			return webClient.post().uri("/auth/login").bodyValue(request).retrieve().bodyToMono(LoginResponseDto.class)
					.block();
		} catch (WebClientResponseException e) {
			if (e.getStatusCode().value() == 403) {
				throw new RuntimeException("Debes verificar tu correo antes de iniciar sesión. Revisa tu bandeja de entrada.");
			}
			return null;
		}
	}

	@Override
	public void cambiarContrasena(String token, String contrasenaActual, String contrasenaNueva) {
		CambiarContrasenaRequestDto request = new CambiarContrasenaRequestDto();
		request.setContrasenaActual(contrasenaActual);
		request.setContrasenaNueva(contrasenaNueva);

		webClient.post().uri("/auth/cambiar-contrasena").header("Authorization", "Bearer " + token)
				.bodyValue(request).retrieve().toBodilessEntity().block();
	}

	@Override
	public void verificarCorreo(String token) {
		webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/auth/verificar-correo").queryParam("token", token).build())
				.retrieve().toBodilessEntity().block();
	}

	@Override
	public void solicitarRecuperacionContrasena(String correo) {
		RecuperarContrasenaRequestDto request = new RecuperarContrasenaRequestDto();
		request.setCorreo(correo);

		webClient.post().uri("/auth/recuperar-contrasena").bodyValue(request).retrieve().toBodilessEntity().block();
	}

	@Override
	public void restablecerContrasena(String token, String contrasenaNueva) {
		RestablecerContrasenaRequestDto request = new RestablecerContrasenaRequestDto();
		request.setToken(token);
		request.setContrasenaNueva(contrasenaNueva);

		webClient.post().uri("/auth/restablecer-contrasena").bodyValue(request).retrieve().toBodilessEntity().block();
	}

}
