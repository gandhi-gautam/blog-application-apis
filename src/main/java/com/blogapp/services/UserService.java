package com.blogapp.services;

import com.blogapp.payloads.PageResponse;
import com.blogapp.payloads.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer userId);

    UserDto getUserById(int userId);

    PageResponse getAllUsers(int pageNumber, int pageSize, String fieldName, String sortDir);

    void deleteUser(int userId);
}
