package com.student.demo.Registration.token;

import java.time.LocalDateTime;

import com.student.demo.appuser.AppUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ConfirmationToken {

    @Id
    @SequenceGenerator(
        name = "confirmation_token_sequence",
        allocationSize = 1,
        sequenceName = "confirmation_token_sequence"
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "confirmation_token_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    @Column(nullable = true)
    private LocalDateTime confirmAt;
    @ManyToOne
    @JoinColumn(
        nullable = false,
        name = "app_user_id"    
    )
    private AppUser appUser;
    
}
