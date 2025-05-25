


package org.workingproject45efs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.workingproject45efs.entity.ConfirmationCode;

import java.util.Optional;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {
    Optional<ConfirmationCode> findByCode(String code);
}