
        package org.workingproject45efs.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.workingproject45efs.dto.carDto.CarResponseDto;
import org.workingproject45efs.dto.managerDto.ManagerRequestDto;
import org.workingproject45efs.dto.managerDto.ManagerResponseDto;
import org.workingproject45efs.service.util.ManagerService;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping
    public ResponseEntity<ManagerResponseDto> createManager(@RequestBody @Valid ManagerRequestDto requestDto) {
        ManagerResponseDto response = managerService.createNewManager(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ManagerResponseDto>> getAllManagers() {
        return ResponseEntity.ok(managerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManagerResponseDto> getManagerById(@PathVariable Integer id) {
        return ResponseEntity.ok(managerService.findById(id));
    }

    // Поиск менеджера по email
    @GetMapping("/by-email")
    public ResponseEntity<ManagerResponseDto> findByEmail(@RequestParam String email) {
        return ResponseEntity.ok(managerService.findByManagerEmail(email));
    }

    // Получение списка машин менеджера по email
    @GetMapping("/cars")
    public ResponseEntity<List<CarResponseDto>> getCarsByManagerEmail(@RequestParam String email) {
        return ResponseEntity.ok(managerService.findCarsByManagerEmail(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Integer id) {
        managerService.deleteManager(id);
        return ResponseEntity.noContent().build();
    }
}