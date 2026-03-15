package edu.bbte.idde.mtim2378.controller;

import edu.bbte.idde.mtim2378.controller.dto.MoveTodoDto;
import edu.bbte.idde.mtim2378.controller.dto.RequestTodoDetailedDto;
import edu.bbte.idde.mtim2378.controller.dto.RequestTodoShortDto;
import edu.bbte.idde.mtim2378.controller.dto.SaveTodoDto;
import edu.bbte.idde.mtim2378.service.TodoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<List<RequestTodoShortDto>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<RequestTodoDetailedDto> getTodo(@Positive @PathVariable Long todoId) {
        return ResponseEntity.ok(todoService.getById(todoId));
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<RequestTodoDetailedDto> updateTodo(
            @Positive @PathVariable Long todoId,
            @Valid @RequestBody SaveTodoDto dto) {
        return ResponseEntity.ok(todoService.updateTodo(todoId, dto));
    }

    @PutMapping("/{todoId}/move")
    public ResponseEntity<RequestTodoDetailedDto> moveTodo(
            @Positive @PathVariable Long todoId,
            @Valid @RequestBody MoveTodoDto dto) {
        return ResponseEntity.ok(todoService.moveTodo(todoId, dto));
    }
}
