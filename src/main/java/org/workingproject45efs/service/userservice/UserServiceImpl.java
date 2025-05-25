package org.workingproject45efs.service.userservice;

import org.workingproject45efs.dto.user.UserRequestDto;
import org.workingproject45efs.dto.user.UserResponseDto;
import org.workingproject45efs.dto.user.UserUpdateRequestDto;
import org.workingproject45efs.entity.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<User> findAllFullDetails() {
        // Здесь должна быть логика получения всех пользователей с полной информацией
        return new ArrayList<>();
    }

    @Override
    public List<UserResponseDto> findAllLimited() {
        // Логика получения пользователей с ограниченной информацией для менеджеров
        return new ArrayList<>();
    }

    @Override
    public UserResponseDto updateUser(UserUpdateRequestDto request) {
        // Логика обновления данных пользователя
        return new UserResponseDto();
    }

    @Override
    public boolean deleteUser(Integer id) {
        // Логика удаления пользователя
        return true;
    }

    @Override
    public UserResponseDto addNewUser(UserRequestDto request) {
        // Логика регистрации нового пользователя
        return new UserResponseDto();
    }

    @Override
    public UserResponseDto findUserById(Integer id) {
        // Поиск пользователя по ID
        return new UserResponseDto();
    }

    @Override
    public UserResponseDto findUserByEmail(String email) {
        // Поиск пользователя по email
        return new UserResponseDto();

    }

}
