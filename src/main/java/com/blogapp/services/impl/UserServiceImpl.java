package com.blogapp.services.impl;

import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.PageResponse;
import com.blogapp.payloads.UserDto;
import com.blogapp.repositories.UserRepo;
import com.blogapp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Create User Api, it will receive userdto and then convert into user then it save in db, after that it convert
     * back to user dto and return user dto
     *
     * @param userDto
     * @return
     */
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoTOUser(userDto);
        User savedUser = userRepo.save(user);
        return userToDto(savedUser);
    }

    /**
     * This method check if the user exists then update
     * if not throw exception resources not found
     *
     * @param userDto
     * @param userId
     * @return
     */
    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = userRepo.save(user);
        return userToDto(updatedUser);
    }

    /**
     * find the user by id, if exists then convert the entity to the user dto if not then raised a
     * resource not found exception
     *
     * @param userId
     * @return
     */
    @Override
    public UserDto getUserById(int userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        return userToDto(user);
    }

    /**
     * This method returns all the users in the database,
     * then we convert each user to user dto and returns list of userDto, this is to be done using stream api
     *
     * @return
     */
    @Override
    public PageResponse getAllUsers(int pageNumber, int pageSize, String fieldName, String sortDir) {
        Pageable pageable = getUserPageableRequest(pageNumber, pageSize, fieldName, sortDir);
        Page<User> users = userRepo.findAll(pageable);
        List<UserDto> userDtos = users.getContent().stream().map(user -> userToDto(user)).collect(Collectors.toList());
        PageResponse response = mapDataToPageResponse(users, userDtos);
        return response;
    }

    /**
     * This method check if the users exists in the system if not then throw exception if yes then save the user and in
     * next step delete the user.
     *
     * @param userId
     */
    @Override
    public void deleteUser(int userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        userRepo.delete(user);
    }

    private User dtoTOUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);

//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setAbout(userDto.getAbout());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
        return user;
    }

    private UserDto userToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    private Pageable getUserPageableRequest(int pageNumber, int pageSize, String fieldName, String sortDir) {
        Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(fieldName).ascending();
        } else {
            sort = Sort.by(fieldName).descending();
        }
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    private PageResponse mapDataToPageResponse(Page<User> users, List<UserDto> userDtos) {
        PageResponse pageResponse = new PageResponse();
        pageResponse.setContent(userDtos);
        pageResponse.setPageNumber(users.getNumber());
        pageResponse.setPageSize(users.getSize());
        pageResponse.setLastPage(users.isLast());
        pageResponse.setTotalPages(users.getTotalPages());
        pageResponse.setTotalElements(users.getTotalElements());
        return pageResponse;
    }
}
