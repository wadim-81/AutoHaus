package org.workingproject45efs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.workingproject45efs.entity.User;

import java.util.Optional;

    @Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User>findByEmail(String email);

        Optional<User> findById(Integer Id);
    }


