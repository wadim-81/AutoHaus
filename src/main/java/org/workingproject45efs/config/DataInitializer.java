package org.workingproject45efs.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.workingproject45efs.entity.Role;
import org.workingproject45efs.repository.RoleRepositoryJpa;
@Profile("!Prod")
@Component
public class DataInitializer implements ApplicationRunner {

    private final RoleRepositoryJpa roleRepository;

    public DataInitializer(RoleRepositoryJpa roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        createRoleIfNotFound("USER");
        createRoleIfNotFound("ADMIN"); // Опционально
    }

    private void createRoleIfNotFound(String roleName) {
        if (!roleRepository.existsByName(roleName)) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }
}