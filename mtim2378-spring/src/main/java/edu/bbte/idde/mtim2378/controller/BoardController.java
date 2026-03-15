package edu.bbte.idde.mtim2378.controller;

import edu.bbte.idde.mtim2378.controller.dto.RequestBoardDto;
import edu.bbte.idde.mtim2378.controller.dto.RequestTodoDetailedDto;
import edu.bbte.idde.mtim2378.controller.dto.RequestTodoShortDto;
import edu.bbte.idde.mtim2378.controller.dto.SaveBoardDto;
import edu.bbte.idde.mtim2378.controller.dto.SaveTodoDto;
import edu.bbte.idde.mtim2378.service.BoardService;
import edu.bbte.idde.mtim2378.service.TodoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
@Profile("datajpa")
public class BoardController {

    private final BoardService boardService;
    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<List<RequestBoardDto>> getAllBoards() {
        return ResponseEntity.ok(boardService.getAllBoards());
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<RequestBoardDto> getBoardById(@Positive @PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.getById(boardId));
    }

    @GetMapping("/{boardId}/todos")
    public ResponseEntity<List<RequestTodoShortDto>> getTodosOfBoard(
            @Positive @PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.getTodosOfBoard(boardId));
    }

    @PostMapping("/{boardId}/todos")
    public ResponseEntity<RequestTodoDetailedDto> addTodo(
            @Valid @RequestBody SaveTodoDto dto,
            @Positive @PathVariable Long boardId
    ) {
        RequestTodoDetailedDto responseDto = todoService.addTodo(boardId, dto);
        URI location = URI.create("api/boards/" + boardId + "/todos/" + responseDto.getId());
        return ResponseEntity.created(location).body(responseDto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<RequestBoardDto> updateBoard(
            @Valid @RequestBody SaveBoardDto dto,
            @Positive @PathVariable Long boardId
    ) {
        RequestBoardDto responseDto = boardService.updateBoard(boardId, dto);
        return ResponseEntity.ok(responseDto);
    }
}
