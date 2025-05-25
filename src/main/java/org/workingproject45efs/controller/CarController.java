package org.workingproject45efs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.workingproject45efs.dto.carDto.CarRequestDto;
import org.workingproject45efs.dto.carDto.CarResponseDto;
import org.workingproject45efs.entity.CarStatus;
import org.workingproject45efs.service.carService.CarService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<CarResponseDto>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDto> getCarById(@PathVariable Integer id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @PostMapping
    public ResponseEntity<CarResponseDto> createCar(@RequestBody CarRequestDto request) {
        CarResponseDto created = carService.createCar(request);
        URI location = URI.create(String.format("/api/cars/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CarResponseDto> updateStatus(@PathVariable Integer id, @RequestParam CarStatus status) {
        CarResponseDto updated = carService.updateCarStatus(id);
        return ResponseEntity.ok(updated);
    }
}
