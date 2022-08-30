package ru.senla.realestatemarket.service.user;

import ru.senla.realestatemarket.dto.jwt.JwtRequestDto;
import ru.senla.realestatemarket.dto.jwt.JwtResponseDto;

public interface IAuthService {

    JwtResponseDto createAuthToken(JwtRequestDto authRequest);

}
