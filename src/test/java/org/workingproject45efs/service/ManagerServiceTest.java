package org.workingproject45efs.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.workingproject45efs.dto.managerDto.ManagerDtoConverter;
import org.workingproject45efs.dto.managerDto.ManagerResponseDto;
import org.workingproject45efs.entity.Manager;
import org.workingproject45efs.repository.ManagerRepositoryJpa;
import org.workingproject45efs.repository.RoleRepositoryJpa;
import org.workingproject45efs.service.exception.NotFoundException;
import org.workingproject45efs.service.util.ManagerService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

    @ExtendWith(MockitoExtension.class)
    class ManagerServiceTest {

        @Mock
        private ManagerRepositoryJpa managerRepository;

        @Mock
        private RoleRepositoryJpa roleRepository;

        @Mock
        private ManagerDtoConverter converter;

        @InjectMocks
        private ManagerService managerService;

        @Test
        void testFindByManagerEmailExists() {
            // подготавливаем сущность Manager
            Manager mgr = new Manager();
            mgr.setId(1);
            mgr.setManagerName("Ivan");
            mgr.setManagerEmail("ivan@example.com");
            Role role = new Role();
            role.setId(2);
            role.setName("User");
            mgr.setRole(role);

            // мок репозитория на тот же e-mail
            when(managerRepository.findByManagerEmail("ivan@example.com"))
                    .thenReturn(Optional.of(mgr));

            // мок конвертера
            ManagerResponseDto dto = new ManagerResponseDto(1, "Ivan", "ivan@example.com", "User");
            when(converter.dtoFromManager(mgr)).thenReturn(dto);

            // вызываем сервис
            ManagerResponseDto result = managerService.findByManagerEmail("ivan@example.com");

            assertNotNull(result, "Должен вернуться DTO");
            assertEquals("Ivan", result.getManagerName());
            assertEquals("User", result.getRoleName());
        }

        @Test
        void testFindByManagerEmailNotFound() {
            when(managerRepository.findByManagerEmail("no@such.com"))
                    .thenReturn(Optional.empty());

            assertThrows(NotFoundException.class,
                    () -> managerService.findByManagerEmail("no@such.com"),
                    "Должно бросаться NotFoundException, если менеджер не найден");
        }
    }



