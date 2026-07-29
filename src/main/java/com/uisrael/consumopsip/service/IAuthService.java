package com.uisrael.consumopsip.service;

import com.uisrael.consumopsip.model.dto.response.LoginResponseDto;

public interface IAuthService {

	LoginResponseDto login(String correo, String contrasena);

	void cambiarContrasena(String token, String contrasenaActual, String contrasenaNueva);

	void verificarCorreo(String token);

	void solicitarRecuperacionContrasena(String correo);

	void restablecerContrasena(String token, String contrasenaNueva);
}
