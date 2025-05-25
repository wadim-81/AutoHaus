package org.workingproject45efs.dto.managerDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManagerResponseDto {

    private Integer id;
    private String managerName;
    private String managerEmail;
    private String  roleName;
}


