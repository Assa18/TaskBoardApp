package edu.bbte.idde.mtim2378.service.impl;

import edu.bbte.idde.mtim2378.controller.dto.LoginRequestDto;
import edu.bbte.idde.mtim2378.controller.dto.RequestUserDetailsDto;
import edu.bbte.idde.mtim2378.controller.dto.SaveUserDto;
import edu.bbte.idde.mtim2378.controller.dto.AuthResponse;
import edu.bbte.idde.mtim2378.mapper.UserMapper;
import edu.bbte.idde.mtim2378.model.User;
import edu.bbte.idde.mtim2378.repository.jpa.JpaUserRepository;
import edu.bbte.idde.mtim2378.service.AuthService;
import edu.bbte.idde.mtim2378.service.exception.ServiceException;
import edu.bbte.idde.mtim2378.service.exception.UserNotFoundException;
import edu.bbte.idde.mtim2378.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public RequestUserDetailsDto registerUser(SaveUserDto dto) {
        try {
            User user = userMapper.toEntity(dto);
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setTheme("light");
            user.setLanguage("eng");

            return userMapper.toDto(userRepository.save(user));
        } catch (DataAccessException ex) {
            log.error(ex.getMessage(), ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public AuthResponse login(LoginRequestDto dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        String token = jwtUtil.generateToken(dto.getEmail());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() ->
                new UserNotFoundException("User not found!"));
        authResponse.setUserId(user.getId());
        authResponse.setTheme(user.getTheme());
        return authResponse;
    }
}
