package edu.bbte.idde.mtim2378.controller.dto;

import edu.bbte.idde.mtim2378.model.Severity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SaveTodoDto {

    @NotEmpty
    private String title;

    @NotNull
    private String description;

    @NotNull
    private LocalDateTime deadline;

    @NotNull
    private Severity severity;
}
