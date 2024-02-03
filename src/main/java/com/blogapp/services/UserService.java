package com.blogapp.services;

import com.blogapp.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer userId);

    UserDto getUserById(int userId);

    List<UserDto> getAllUsers();

    void deleteUser(int userId);
}
