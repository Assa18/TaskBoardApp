package edu.bbte.idde.mtim2378.service;

import edu.bbte.idde.mtim2378.controller.dto.RequestBoardDto;
import edu.bbte.idde.mtim2378.controller.dto.SetThemeDto;

import java.util.List;

public interface UserService {

    List<RequestBoardDto> getBoardsOfUser(Long userId);

    void setTheme(Long userId, SetThemeDto dto);
}
