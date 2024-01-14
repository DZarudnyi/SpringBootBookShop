package com.example.books.service.user;

import com.example.books.dto.user.UserRegistrationRequestDto;
import com.example.books.dto.user.UserResponseDto;
import com.example.books.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;
}
