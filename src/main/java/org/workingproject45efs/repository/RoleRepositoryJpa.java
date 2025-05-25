package org.workingproject45efs.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.workingproject45efs.entity.Role;

import java.util.Optional;
@Repository
public interface RoleRepositoryJpa extends JpaRepository<Role, Integer> {
    boolean existsByName(String name);
    Optional<Role> findByName(String name);
}
