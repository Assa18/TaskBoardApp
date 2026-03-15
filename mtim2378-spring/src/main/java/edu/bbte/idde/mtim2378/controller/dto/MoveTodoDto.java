package edu.bbte.idde.mtim2378.controller.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MoveTodoDto {

    @Positive
    private Long newBoardId;
}
