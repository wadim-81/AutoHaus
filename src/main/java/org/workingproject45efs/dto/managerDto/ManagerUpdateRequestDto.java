package org.workingproject45efs.dto.managerDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManagerUpdateRequestDto {
    private Integer id;
    private String managerEmail;

    @NotBlank (message = "First name is required and must be not blank")
    @Size (min=3, max= 15,message = "First name length not correct")

    private String firstName;
    private String LastName;
    private String hashPassword;
}
