package org.workingproject45efs.dto.managerDto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerRequestDto {

    private String managerName;
    private String managerPassword;
    @Email
    private String managerEmail;
}


