package edu.bbte.idde.mtim2378.controller.dto;

import lombok.Data;

@Data
public class RequestUserDetailsDto {

    private Long id;

    private String email;

    private String theme;

    private String language;
}
