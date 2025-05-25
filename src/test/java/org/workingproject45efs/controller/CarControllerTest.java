package org.workingproject45efs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.workingproject45efs.dto.carDto.CarRequestDto;
import org.workingproject45efs.dto.carDto.CarResponseDto;
import org.workingproject45efs.entity.CarStatus;
import org.workingproject45efs.service.carService.CarService;
import org.workingproject45efs.service.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(CarController.class)
@Import(CarControllerTest.MockConfig.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarService carService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public CarService carService() {
            return Mockito.mock(CarService.class);
        }
    }

    @Test
    @DisplayName("GET /api/cars returns empty list")
    public void testGetAllCarsEmpty() throws Exception {
        Mockito.when(carService.getAllCars()).thenReturn(Collections.emptyList());

        mockMvc.perform((RequestBuilder) get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("GET /api/cars returns list of cars")
    public void testGetAllCars() throws Exception {
        // Обратите внимание: правильный порядок параметров в конструкторе CarResponseDto
        CarResponseDto car1 = new CarResponseDto(1, "BrandA", "ModelA", 2020, "Red", 10000.0, LocalDateTime.now(), CarStatus.AVAILABLE, "manager@example.com");
        CarResponseDto car2 = new CarResponseDto(2, "BrandB", "ModelB", 2021, "Blue", 15000.0, LocalDateTime.now(), CarStatus.SOLD, "manager2@example.com");
        List<CarResponseDto> cars = Arrays.asList(car1, car2);
        Mockito.when(carService.getAllCars()).thenReturn(cars);

        mockMvc.perform((RequestBuilder) get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    @DisplayName("GET /api/cars/{id} returns a car")
    public void testGetCarById() throws Exception {
        CarResponseDto car = new CarResponseDto(1, "BrandA", "ModelA", 2020, "Red", 10000.0, LocalDateTime.now(), CarStatus.AVAILABLE, "manager@example.com");
        Mockito.when(carService.getCarById(1)).thenReturn(car);

        mockMvc.perform(get("/api/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.brand").value("BrandA"))
                .andExpect(jsonPath("$.model").value("ModelA"))
                .andExpect(jsonPath("$.color").value("Red"))
                .andExpect(jsonPath("$.price").value(10000.0))
                .andExpect(jsonPath("$.status").value("AVAILABLE"))
                .andExpect(jsonPath("$.managerEmail").value("manager@example.com"));
    }

    @Test
    @DisplayName("GET /api/cars/{id} returns 404 when not found")
    public void testGetCarByIdNotFound() throws Exception {
        Mockito.when(carService.getCarById(1)).thenThrow(new NotFoundException("Car not found"));

        mockMvc.perform(get("/api/cars/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Car not found"));
    }

    @Test
    @DisplayName("POST /api/cars creates a new car")
    public void testCreateCar() throws Exception {
        CarRequestDto request = new CarRequestDto();
        request.setBrand("BrandC");
        request.setModel("ModelC");
        request.setYear(2022);
        request.setColor("Green");
        request.setPrice(20000.0);
        request.setManagerEmail("manager@example.com");

        CarResponseDto created = new CarResponseDto(3, "BrandC", "ModelC", 2022, "Green", 20000.0, LocalDateTime.now(), CarStatus.AVAILABLE, "manager@example.com");
        Mockito.when(carService.createCar(any(CarRequestDto.class))).thenReturn(created);

        mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/cars/3"))
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    @DisplayName("POST /api/cars returns 400 on invalid input")
    void testCreateCarInvalidInput() throws Exception {
        CarRequestDto request = new CarRequestDto(); // пустой объект — невалидный

        mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PATCH /api/cars/{id}/status updates status")
    void testUpdateStatus() throws Exception {
        CarResponseDto updated = new CarResponseDto(1, "BrandA", "ModelA", 2020, "Red", 10000.0, LocalDateTime.now(), CarStatus.SOLD, "manager@example.com");
        Mockito.when(carService.updateCarStatus(eq(1), eq(CarStatus.SOLD))).thenReturn(updated);

        mockMvc.perform(patch("/api/cars/1/status").param("status", "SOLD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SOLD"));
    }

    @Test
    @DisplayName("PATCH /api/cars/{id}/status returns 404 when car not found")
    public void testUpdateStatusNotFound() throws Exception {
        Mockito.when(carService.updateCarStatus(eq(999), eq(CarStatus.SOLD)))
                .thenThrow(new NotFoundException("Car not found"));

        mockMvc.perform(patch("/api/cars/999/status").param("status", "SOLD"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Car not found"));
    }

    @Test
    @DisplayName("PATCH /api/cars/{id}/status returns 400 on invalid status")
    void testUpdateStatusInvalid() throws Exception {
        // Если передается неправильное значение статуса, то ожидается 400 (Bad Request)
        mockMvc.perform(patch("/api/cars/1/status").param("status", "INVALID"))
                .andExpect(status().isBadRequest());
    }
}


