package org.workingproject45efs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.workingproject45efs.dto.managerDto.ManagerRequestDto;
import org.workingproject45efs.service.registration.RegistrationService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RegistrationService registrationService;

    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody ManagerRequestDto dto) {
        registrationService.register(dto);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/confirm")
    public ResponseEntity<Void> confirm(@RequestParam String code) {
        registrationService.confirm(code);
        return ResponseEntity.ok().build();
    }
}
