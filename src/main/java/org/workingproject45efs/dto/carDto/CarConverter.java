package org.workingproject45efs.dto.carDto;


import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.workingproject45efs.entity.Car;
import org.workingproject45efs.entity.CarStatus;
import org.workingproject45efs.service.util.ManagerService;

import java.time.LocalDateTime;

@Component
public class CarConverter {

    private final ManagerService managerService;

    public CarConverter(@Lazy ManagerService managerService) {
        this.managerService = managerService;
    }

    public Car toEntity(CarRequestDto dto) {
        Car car = new Car();
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setYear(dto.getYear());
        car.setColor(dto.getColor());
        car.setPrice(dto.getPrice());
        car.setDateAdded(LocalDateTime.now());
        car.setStatus(CarStatus.AVAILABLE);

        car.setManager(
                managerService.findManagerEntityByEmail(dto.getManagerEmail())
                        .orElseThrow(() -> new RuntimeException(
                                "Менеджер с email '" + dto.getManagerEmail() + "' не найден"))
        );

        return car;
    }

    public CarResponseDto toDto(Car car) {
        if (car == null) return  null;
        CarResponseDto dto = new CarResponseDto();
        dto.setId(car.getId());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setYear(car.getYear());
        dto.setColor(car.getColor());
        dto.setPrice(car.getPrice());
        dto.setStatus(car.getStatus());
        dto.setDateAdded(car.getDateAdded());

        if (car.getManager() != null) {
            dto.setManagerEmail(car.getManager().getManagerEmail());
        }
        return dto;
    }
}

