package edu.bbte.idde.mtim2378.mapper;

import edu.bbte.idde.mtim2378.controller.dto.SaveTodoDto;
import edu.bbte.idde.mtim2378.controller.dto.RequestTodoDetailedDto;
import edu.bbte.idde.mtim2378.controller.dto.RequestTodoShortDto;
import edu.bbte.idde.mtim2378.model.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    RequestTodoShortDto toRequestShortDto(Todo todo);

    RequestTodoDetailedDto toRequestDetailedDto(Todo todo);

    Todo toTodo(SaveTodoDto todo);
}
