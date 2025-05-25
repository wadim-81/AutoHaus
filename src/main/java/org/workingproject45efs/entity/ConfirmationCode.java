package org.workingproject45efs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
public class ConfirmationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private LocalDateTime expiresAt;
    private boolean used = false;

    @Column(length = 1000)
    private String link;

    @OneToOne(fetch = FetchType.LAZY)
    private Manager manager;
}
