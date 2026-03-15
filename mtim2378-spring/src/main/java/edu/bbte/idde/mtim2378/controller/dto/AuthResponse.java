package edu.bbte.idde.mtim2378.controller.dto;

import lombok.Data;

@Data
public class AuthResponse {

    private String token;

    private Long userId;

    private String theme;
}
