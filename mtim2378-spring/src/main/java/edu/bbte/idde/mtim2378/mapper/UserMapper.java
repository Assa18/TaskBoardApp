package edu.bbte.idde.mtim2378.mapper;

import edu.bbte.idde.mtim2378.controller.dto.RequestUserDetailsDto;
import edu.bbte.idde.mtim2378.controller.dto.SaveUserDto;
import edu.bbte.idde.mtim2378.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    RequestUserDetailsDto toDto(User user);

    User toEntity(SaveUserDto dto);


}
