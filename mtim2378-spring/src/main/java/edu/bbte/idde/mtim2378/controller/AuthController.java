package edu.bbte.idde.mtim2378.controller;

import edu.bbte.idde.mtim2378.controller.dto.LoginRequestDto;
import edu.bbte.idde.mtim2378.controller.dto.SaveUserDto;
import edu.bbte.idde.mtim2378.controller.dto.AuthResponse;
import edu.bbte.idde.mtim2378.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody SaveUserDto dto) {
        authService.registerUser(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequestDto dto) {
        AuthResponse authResponse = authService.login(dto);
        return ResponseEntity.ok(authResponse);
    }
}
