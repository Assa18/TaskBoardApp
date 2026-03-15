package edu.bbte.idde.mtim2378.service;

import edu.bbte.idde.mtim2378.controller.dto.RequestBoardDto;
import edu.bbte.idde.mtim2378.controller.dto.RequestTodoShortDto;
import edu.bbte.idde.mtim2378.controller.dto.SaveBoardDto;

import java.util.List;

public interface BoardService {

    RequestBoardDto getById(Long id);

    List<RequestBoardDto> getAllBoards();

    List<RequestTodoShortDto> getTodosOfBoard(Long id);

    RequestBoardDto createBoard(Long userId, SaveBoardDto dto);

    void deleteBoard(Long id);

    RequestBoardDto updateBoard(Long id, SaveBoardDto dto);
}
