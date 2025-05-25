package org.workingproject45efs.service.registration;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.workingproject45efs.dto.managerDto.ManagerRequestDto;
import org.workingproject45efs.dto.managerDto.ManagerResponseDto;
import org.workingproject45efs.entity.ConfirmationCode;
import org.workingproject45efs.repository.ConfirmationCodeRepository;
import org.workingproject45efs.repository.mapper.ManagerMapper;
import org.workingproject45efs.service.mail.TemplateProjectMailSender;
import org.workingproject45efs.service.util.ManagerService;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RegistrationService {
    private final boolean isEmailEnabled;
    private final ManagerService managerService;
    private final ConfirmationCodeRepository codeRepo;
    private final TemplateProjectMailSender mailSender;
    private final ManagerMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(
            ManagerService managerService,
            ConfirmationCodeRepository codeRepo,
            TemplateProjectMailSender mailSender,
            ManagerMapper mapper,
            PasswordEncoder passwordEncoder,
            @Value("${app.email.enabled:false}") boolean isEmailEnabled // disable post
    ) {
        this.managerService = managerService;
        this.codeRepo = codeRepo;
        this.mailSender = mailSender;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.isEmailEnabled = isEmailEnabled; // disable post
    }

    @Transactional
    public void register(ManagerRequestDto dto) {
        // Шифрование пароля
        String encodedPassword = passwordEncoder.encode(dto.getManagerPassword());
        dto.setManagerPassword(encodedPassword);

        ManagerResponseDto created = managerService.createNewManager(dto);

        //verification code
        ConfirmationCode code = new ConfirmationCode();
        code.setCode(UUID.randomUUID().toString());
        code.setExpiresAt(LocalDateTime.now().plusDays(1));
        code.setManager(mapper.toEntity(created));
        codeRepo.save(code);

        // conditional sending of email
        if (isEmailEnabled) {
            mailSender.sendConfirmationEmail(created.getManagerEmail(), code.getCode());
        }
    }

    @Transactional
    public void confirm(String code) {
        ConfirmationCode cc = codeRepo.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("invalid verification code"));

        if (cc.isUsed()) {
            throw new IllegalStateException("code already used");
        }

        if (cc.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("expired");
        }

        cc.setUsed(true);
        codeRepo.save(cc);
    }
}