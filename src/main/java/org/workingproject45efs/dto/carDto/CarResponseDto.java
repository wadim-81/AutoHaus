package org.workingproject45efs.dto.carDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.workingproject45efs.entity.CarStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponseDto {
    private Integer id;
    private String brand;
    private String model;
    private int year;
    private String color;
    private double price;
    private LocalDateTime dateAdded;
    private CarStatus status;
    private String managerEmail;
}
