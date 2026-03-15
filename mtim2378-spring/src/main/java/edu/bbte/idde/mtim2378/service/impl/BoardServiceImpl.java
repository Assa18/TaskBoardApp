package edu.bbte.idde.mtim2378.service.impl;

import edu.bbte.idde.mtim2378.controller.dto.RequestBoardDto;
import edu.bbte.idde.mtim2378.controller.dto.RequestTodoShortDto;
import edu.bbte.idde.mtim2378.controller.dto.SaveBoardDto;
import edu.bbte.idde.mtim2378.mapper.BoardMapper;
import edu.bbte.idde.mtim2378.mapper.TodoMapper;
import edu.bbte.idde.mtim2378.model.Board;
import edu.bbte.idde.mtim2378.model.User;
import edu.bbte.idde.mtim2378.repository.jpa.JpaBoardRepository;
import edu.bbte.idde.mtim2378.repository.jpa.JpaUserRepository;
import edu.bbte.idde.mtim2378.service.BoardService;
import edu.bbte.idde.mtim2378.service.exception.BoardNotFoundException;
import edu.bbte.idde.mtim2378.service.exception.ServiceException;
import edu.bbte.idde.mtim2378.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("datajpa")
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final JpaBoardRepository boardRepository;
    private final JpaUserRepository userRepository;
    private final BoardMapper boardMapper;
    private final TodoMapper todoMapper;

    @Override
    public RequestBoardDto getById(Long id) {
        try {
            if (!boardRepository.existsById(id)) {
                throw new BoardNotFoundException("Board with id " + id + "not found!");
            }
            return boardMapper.toDto(boardRepository.findById(id).get());
        } catch (DataAccessException e) {
            log.error("Failed to get board by id!", e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<RequestBoardDto> getAllBoards() {
        try {
            return boardRepository.findAll().stream().map(boardMapper::toDto).toList();
        } catch (DataAccessException e) {
            log.error("Failed to get boards!", e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<RequestTodoShortDto> getTodosOfBoard(Long id) {
        try {
            if (!boardRepository.existsById(id)) {
                throw new BoardNotFoundException("Board with id " + id + "not found!");
            }
            return boardRepository.findById(id).get().getTodos().stream()
                    .map(todoMapper::toRequestShortDto)
                    .toList();
        } catch (DataAccessException e) {
            log.error("Failed to get board by id!", e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public RequestBoardDto createBoard(Long userId, SaveBoardDto dto) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new UserNotFoundException("User with id " + userId + "not found!"));
            Board board = boardMapper.toEntity(dto);
            board.setUser(user);

            return boardMapper.toDto(boardRepository.save(board));
        } catch (DataAccessException e) {
            log.error("Failed to create board!", e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteBoard(Long id) {
        try {
            if (!boardRepository.existsById(id)) {
                throw new BoardNotFoundException("Board with id " + id + "not found!");
            }
            boardRepository.deleteById(id);
        } catch (DataAccessException e) {
            log.error("Failed to delete board by id!", e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public RequestBoardDto updateBoard(Long id, SaveBoardDto dto) {
        try {
            Board board = boardRepository.findById(id).orElseThrow(() ->
                    new BoardNotFoundException("Board with id " + id + "not found!"));
            board.setId(id);
            board.setTitle(dto.getTitle());
            return boardMapper.toDto(boardRepository.save(board));
        } catch (DataAccessException e) {
            log.error("Failed to update board!", e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
