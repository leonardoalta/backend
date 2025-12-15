package com.blacksystem.system.models.email;

import com.blacksystem.system.models.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "verification_codes")
@Getter
@Setter
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_verification_code")
    private Long idVerificationCode;

    @Column(name = "code", length = 10)
    private String code;

    @Column(name = "expiry_at")
    private LocalDateTime expiryAt;

    @Column(name = "attempts")
    private int attempts = 0;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private User user;

    public Long getIdVerificationCode() {
        return idVerificationCode;
    }

    public void setIdVerificationCode(Long idVerificationCode) {
        this.idVerificationCode = idVerificationCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpiryAt() {
        return expiryAt;
    }

    public void setExpiryAt(LocalDateTime expiryAt) {
        this.expiryAt = expiryAt;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}