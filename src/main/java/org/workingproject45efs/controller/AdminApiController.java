package org.workingproject45efs.controller;

import org.workingproject45efs.dto.user.UserResponseDto;
import org.workingproject45efs.dto.user.UserUpdateRequestDto;
import org.workingproject45efs.entity.User;
import org.workingproject45efs.repository.api.AdminApi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.workingproject45efs.service.userservice.UserService;

import java.util.List;

@RestController
public class AdminApiController implements AdminApi {

    private final UserService userService;

    public AdminApiController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<User>> findAllFullDetails() {
        List<User> users = userService.findAllFullDetails();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<UserResponseDto> dtos = userService.findAllLimited();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<UserResponseDto> updateUser(UserUpdateRequestDto request) {
        UserResponseDto updated = userService.updateUser(request);
        return ResponseEntity.ok(updated);
    }

    @Override
    public boolean deleteUser(Integer id) {
        return userService.deleteUser(id);
    }
}
