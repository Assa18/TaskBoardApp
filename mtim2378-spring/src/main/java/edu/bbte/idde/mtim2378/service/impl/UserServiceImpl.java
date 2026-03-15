package edu.bbte.idde.mtim2378.service.impl;

import edu.bbte.idde.mtim2378.controller.dto.RequestBoardDto;
import edu.bbte.idde.mtim2378.controller.dto.SetThemeDto;
import edu.bbte.idde.mtim2378.mapper.BoardMapper;
import edu.bbte.idde.mtim2378.model.User;
import edu.bbte.idde.mtim2378.repository.jpa.JpaUserRepository;
import edu.bbte.idde.mtim2378.service.UserService;
import edu.bbte.idde.mtim2378.service.exception.ServiceException;
import edu.bbte.idde.mtim2378.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JpaUserRepository userRepository;
    private final BoardMapper boardMapper;

    @Override
    public List<RequestBoardDto> getBoardsOfUser(Long userId) {
        try {
            if (!userRepository.existsById(userId)) {
                throw new UserNotFoundException("User not found with id: " + userId);
            }

            return userRepository.findById(userId).get().getBoards().stream()
                    .map(boardMapper::toDto)
                    .toList();
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setTheme(Long userId, SetThemeDto dto) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new UserNotFoundException("User not found with id: " + userId));

            user.setTheme(dto.getTheme());
            userRepository.save(user);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
