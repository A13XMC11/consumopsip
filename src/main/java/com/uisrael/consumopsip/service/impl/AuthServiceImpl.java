package com.uisrael.consumopsip.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.uisrael.consumopsip.model.dto.request.LoginRequestDto;
import com.uisrael.consumopsip.model.dto.response.LoginResponseDto;
import com.uisrael.consumopsip.service.IAuthService;

@Service
public class AuthServiceImpl {

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
			return null;
		}
	}

}
