package org.workingproject45efs.dto.managerDto;

import org.springframework.stereotype.Component;
import org.workingproject45efs.entity.Manager;
import org.workingproject45efs.service.exception.NotFoundException;

@Component
public class ManagerDtoConverter {

    public Manager managerFromDto(ManagerRequestDto dto) {
        Manager manager = new Manager();
        manager.setManagerName(dto.getManagerName());
        manager.setManagerEmail(dto.getManagerEmail());
        manager.setManagerPassword(dto.getManagerPassword());
        return manager;
    }

    public ManagerResponseDto dtoFromManager(Manager manager) {
        if (manager.getRole() == null) {
            throw new NotFoundException("У менеджера с id=" + manager.getId() + " не задана роль");
        }
        return ManagerResponseDto.builder()
                .id(manager.getId())
                .managerName(manager.getManagerName())
                .managerEmail(manager.getManagerEmail())
                .roleName(manager.getRole().getName())
                .build();
    }
}
