package com.uisrael.consumopsip.service;

import com.uisrael.consumopsip.model.dto.response.LoginResponseDto;

public interface IAuthService {

	LoginResponseDto login(String correo, String contrasena);
}
