package org.workingproject45efs.dto.carDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRequestDto {
    private String brand;
    private String model;
    private int year;
    private String color;
    private double price;
    private String managerEmail;
}


