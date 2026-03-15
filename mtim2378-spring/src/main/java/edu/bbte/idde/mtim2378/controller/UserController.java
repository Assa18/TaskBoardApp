package edu.bbte.idde.mtim2378.controller;

import edu.bbte.idde.mtim2378.controller.dto.RequestBoardDto;
import edu.bbte.idde.mtim2378.controller.dto.SaveBoardDto;
import edu.bbte.idde.mtim2378.controller.dto.SetThemeDto;
import edu.bbte.idde.mtim2378.service.BoardService;
import edu.bbte.idde.mtim2378.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final BoardService boardService;
    private final UserService userService;

    @PostMapping("/{userId}/boards")
    public ResponseEntity<RequestBoardDto> createBoard(
            @Positive @PathVariable Long userId,
            @Valid @RequestBody SaveBoardDto dto) {
        RequestBoardDto respDto = boardService.createBoard(userId, dto);
        URI location = URI.create("api/ " + userId + "/boards/" + respDto.getId());
        return ResponseEntity.created(location).body(respDto);
    }

    @GetMapping("/{userId}/boards")
    public ResponseEntity<List<RequestBoardDto>> getBoardsOfUser(
            @Positive @PathVariable Long userId) {
        return ResponseEntity.ok(userService.getBoardsOfUser(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> setTheme(
            @Positive @PathVariable Long userId,
            @Valid @RequestBody SetThemeDto dto
    ) {
        userService.setTheme(userId, dto);
        return ResponseEntity.ok().build();
    }
}
