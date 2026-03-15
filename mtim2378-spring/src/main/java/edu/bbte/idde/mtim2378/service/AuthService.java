package edu.bbte.idde.mtim2378.service;

import edu.bbte.idde.mtim2378.controller.dto.LoginRequestDto;
import edu.bbte.idde.mtim2378.controller.dto.RequestUserDetailsDto;
import edu.bbte.idde.mtim2378.controller.dto.SaveUserDto;
import edu.bbte.idde.mtim2378.controller.dto.AuthResponse;

public interface AuthService {

    RequestUserDetailsDto registerUser(SaveUserDto dto);

    AuthResponse login(LoginRequestDto dto);
}
