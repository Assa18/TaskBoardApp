package edu.bbte.idde.mtim2378.controller.dto;

import edu.bbte.idde.mtim2378.model.Severity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestTodoDetailedDto {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime deadline;

    private Severity severity;
}
