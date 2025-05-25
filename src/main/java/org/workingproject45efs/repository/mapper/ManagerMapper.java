package org.workingproject45efs.repository.mapper;

import org.mapstruct.Mapper;
import org.workingproject45efs.dto.managerDto.ManagerResponseDto;
import org.workingproject45efs.entity.Manager;

@Mapper(componentModel = "spring")
public interface ManagerMapper {
    Manager toEntity(ManagerResponseDto dto);
}