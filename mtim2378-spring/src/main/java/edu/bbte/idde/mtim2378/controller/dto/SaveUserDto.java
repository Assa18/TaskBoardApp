package edu.bbte.idde.mtim2378.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveUserDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
