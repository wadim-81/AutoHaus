package org.workingproject45efs.controller;

import org.workingproject45efs.dto.user.UserRequestDto;
import org.workingproject45efs.dto.user.UserResponseDto;
import org.workingproject45efs.repository.api.PublicApi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.workingproject45efs.service.userservice.UserService;

@RestController
public class PublicApiController implements PublicApi {

    private final UserService userService;

    public PublicApiController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserResponseDto> addNewUser(UserRequestDto request) {
        UserResponseDto created = userService.addNewUser(request);
        return ResponseEntity.status(201).body(created);
    }
}
