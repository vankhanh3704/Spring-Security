package com.devteria.identify_service.controller;


import com.devteria.identify_service.Entity.UserEntity;
import com.devteria.identify_service.Service.UserService;
import com.devteria.identify_service.dto.request.ApiResponse;
import com.devteria.identify_service.dto.request.UserCreationRequest;
import com.devteria.identify_service.dto.request.UserUpdateRequest;
import com.devteria.identify_service.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {
    UserService userService;

    @PostMapping
    private ApiResponse<UserEntity> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserEntity> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping
    private List<UserEntity> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    private UserResponse getUser(@PathVariable String userId){
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    private UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    private String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "User deleted";
    }
}
