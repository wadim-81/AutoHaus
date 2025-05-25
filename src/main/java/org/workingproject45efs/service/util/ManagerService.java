package org.workingproject45efs.service.util;


import org.springframework.context.annotation.Lazy;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.workingproject45efs.dto.carDto.CarConverter;
import org.workingproject45efs.dto.carDto.CarResponseDto;
import org.workingproject45efs.dto.managerDto.ManagerRequestDto;
import org.workingproject45efs.dto.managerDto.ManagerResponseDto;
import org.workingproject45efs.dto.managerDto.ManagerDtoConverter;

import org.workingproject45efs.entity.Manager;
import org.workingproject45efs.entity.Role;
import org.workingproject45efs.repository.CarRepository;
import org.workingproject45efs.repository.ManagerRepositoryJpa;
import org.workingproject45efs.repository.RoleRepositoryJpa;
import org.workingproject45efs.service.exception.AlreadyExistsException;
import org.workingproject45efs.service.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManagerService {
    private final ManagerRepositoryJpa repository;
    private final RoleRepositoryJpa roleRepository;
    private final ManagerDtoConverter converter;
    private final CarRepository carRepository;
    private final CarConverter carConverter;
    private  final PasswordEncoder passwordEncoder;

    public ManagerService(
            ManagerRepositoryJpa repository,
            RoleRepositoryJpa roleRepository,
            ManagerDtoConverter converter,
            CarRepository carRepository,
            @Lazy CarConverter carConverter,
            PasswordEncoder passwordEncoder) { // Inject PasswordEncoder
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.converter = converter;
        this.carRepository = carRepository;
        this.carConverter = carConverter;
        this.passwordEncoder = passwordEncoder;
    }

    public ManagerResponseDto createNewManager(ManagerRequestDto request) {
        if (repository.findByManagerEmail(request.getManagerEmail()).isPresent()) {
            throw new AlreadyExistsException("Пользователь с email "
                    + request.getManagerEmail() + " уже зарегистрирован");
        }
        Manager newManager = converter.managerFromDto(request);
        // Encode the password before saving
        newManager.setManagerPassword(passwordEncoder.encode(request.getManagerPassword()));
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new NotFoundException( // Changed to NotFoundException
                        "Роль 'USER' не найдена"));
        newManager.setRole(userRole);
        Manager savedManager = repository.save(newManager);
        return converter.dtoFromManager(savedManager);
    }

    public List<ManagerResponseDto> findAll() {
        return repository.findAll().stream()
                .map(converter::dtoFromManager)
                .collect(Collectors.toList());
    }

    public ManagerResponseDto findById(Integer id) {
        Manager manager = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Manager with id=" + id + " not found"));
        return converter.dtoFromManager(manager);
    }

    public ManagerResponseDto findByManagerEmail(String email) {
        Manager manager = repository.findByManagerEmail(email)
                .orElseThrow(() -> new NotFoundException(
                        "Менеджер с email '" + email + "' не найден"));
        return converter.dtoFromManager(manager);
    }

    public void deleteManager(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Менеджер с таким ID не найден");
        }
        repository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<CarResponseDto> getAllCars() {
        return carRepository.findAllWithManager().stream()
                .map(carConverter::toDto)
                .collect(Collectors.toList());
    }



    public Optional<Manager> findManagerEntityByEmail(String email) {
        return repository.findByManagerEmail(email);
    }



    public List<CarResponseDto> findCarsByManagerEmail(String email) {
        Manager manager = repository.findByManagerEmail(email)
                .orElseThrow(() -> new NotFoundException(
                        "Менеджер с email '" + email + "' не найден"));
        return carRepository.findByManagerId(manager.getId()).stream()
                .map(carConverter::toDto)
                .collect(Collectors.toList());
    }
}

