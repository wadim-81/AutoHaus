package org.workingproject45efs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.workingproject45efs.entity.Manager;

import java.util.*;
@Repository

public interface ManagerRepositoryJpa extends JpaRepository<Manager, Integer> {

    boolean existsByManagerEmail (String email);

    Optional<Manager> findByManagerEmail(String email);
    List<Manager> findByManagerName(String name);
}
