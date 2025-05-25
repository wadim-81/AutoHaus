package org.workingproject45efs.repository.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.workingproject45efs.dto.user.ErrorResponseDto;
import org.workingproject45efs.dto.user.UserResponseDto;
import org.workingproject45efs.dto.user.UserUpdateRequestDto;
import org.workingproject45efs.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin")
public interface AdminApi {

    // Найти всех пользователей (полная информация – для ADMIN)
    @GetMapping("/full")
    ResponseEntity<List<User>> findAllFullDetails();

    // Найти всех пользователей (ограниченная информация – для MANAGER)
    @GetMapping("/manager/all")
    ResponseEntity<List<UserResponseDto>> findAll();

    // Обновить данные пользователя
    @PutMapping("/update")
    ResponseEntity<UserResponseDto> updateUser(@RequestBody UserUpdateRequestDto request);

    // Удалить пользователя
    @DeleteMapping("/{id}")
    boolean deleteUser(@PathVariable Integer id);
}
