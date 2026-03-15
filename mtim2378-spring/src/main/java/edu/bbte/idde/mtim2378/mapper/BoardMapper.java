package edu.bbte.idde.mtim2378.mapper;

import edu.bbte.idde.mtim2378.controller.dto.RequestBoardDto;
import edu.bbte.idde.mtim2378.controller.dto.SaveBoardDto;
import edu.bbte.idde.mtim2378.model.Board;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    RequestBoardDto toDto(Board board);

    Board toEntity(SaveBoardDto dto);
}
