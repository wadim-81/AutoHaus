package org.workingproject45efs.controller;

import org.workingproject45efs.dto.user.UserResponseDto;
import org.workingproject45efs.repository.api.UserApi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.workingproject45efs.service.userservice.UserService;

@RestController
public class UserApiController implements UserApi {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserResponseDto> findUserById(Integer id) {
        UserResponseDto dto = userService.findUserById(id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<UserResponseDto> findUserByEmail(String email) {
        UserResponseDto dto = userService.findUserByEmail(email);
        return ResponseEntity.ok(dto);
    }
}
