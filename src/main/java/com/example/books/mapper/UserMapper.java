package com.example.books.mapper;

import com.example.books.config.MapperConfig;
import com.example.books.dto.user.UserResponseDto;
import com.example.books.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface UserMapper {
    UserResponseDto toUserResponse(User user);
}
