package com.blogapp.controllers;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.UserDto;
import com.blogapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createUserDto = userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @Valid @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize) {
        return ResponseEntity.ok(userService.getAllUsers(pageNumber, pageSize));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
}
