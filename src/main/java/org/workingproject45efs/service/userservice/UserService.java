package org.workingproject45efs.service.userservice;

import org.workingproject45efs.dto.user.UserRequestDto;
import org.workingproject45efs.dto.user.UserResponseDto;
import org.workingproject45efs.dto.user.UserUpdateRequestDto;
import org.workingproject45efs.entity.User;
import java.util.List;

public interface UserService {
    List<User> findAllFullDetails();
    List<UserResponseDto> findAllLimited();
    UserResponseDto updateUser(UserUpdateRequestDto request);
    boolean deleteUser(Integer id);
    UserResponseDto addNewUser(UserRequestDto request);
    UserResponseDto findUserById(Integer id);
    UserResponseDto findUserByEmail(String email);
}
