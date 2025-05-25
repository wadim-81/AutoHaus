package org.workingproject45efs.service.carService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.workingproject45efs.dto.carDto.CarConverter;
import org.workingproject45efs.dto.carDto.CarRequestDto;
import org.workingproject45efs.dto.carDto.CarResponseDto;
import org.workingproject45efs.entity.Car;
import org.workingproject45efs.entity.CarStatus;
import org.workingproject45efs.repository.CarRepository;
import org.workingproject45efs.service.exception.NotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarConverter carConverter;

    public CarService(CarRepository carRepository, CarConverter carConverter) {
        this.carRepository = carRepository;
        this.carConverter = carConverter;
    }

    @Transactional(readOnly = true)
    public List<CarResponseDto> getAllCars() {
        return carRepository.findAll().stream()
                .map(carConverter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Map<String, Long> countCars() {
        long total = carRepository.count();
        return Collections.singletonMap("totalCars", total);
    }

    @Transactional(readOnly = true)
    public CarResponseDto getCarById(int id) {
        return carRepository.findById(id)
                .map(carConverter::toDto)
                .orElseThrow(() -> new NotFoundException("Car not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<CarResponseDto> getCarsByBrand(String brand) {
        return carRepository.findByBrand(brand).stream()
                .map(carConverter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CarResponseDto> getCarsByModel(String model) {
        return carRepository.findByModel(model).stream()
                .map(carConverter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CarResponseDto> getCarsByManagerId(Integer managerId) {
        return carRepository.findByManagerId(managerId).stream()
                .map(carConverter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CarResponseDto> getCarsByManagerEmail(String email) {
        return carRepository.findByManager_ManagerEmail(email).stream()
                .map(carConverter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CarResponseDto createCar(CarRequestDto carDto) {
        Car car = carConverter.toEntity(carDto);
        Car savedCar = carRepository.save(car);
        return carConverter.toDto(savedCar);
    }

    @Transactional
    public CarResponseDto updateCarStatus(Integer carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Car not found with id: " + carId));
        // Логика обновления статуса может быть дополнена
        Car updated = carRepository.save(car);
        return carConverter.toDto(updated);
    }
    @Transactional
    public CarResponseDto updateCarStatus(Integer carId, CarStatus newStatus) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Car not found with id: " + carId));
        car.setStatus(newStatus); // Обновляем статус автомобиля
        Car updated = carRepository.save(car);
        return carConverter.toDto(updated);
    }


    @Transactional
    public void deleteCar(Integer carId) {
        if (!carRepository.existsById(carId)) {
            throw new NotFoundException("Car not found with id: " + carId);
        }
        carRepository.deleteById(carId);
    }


}
