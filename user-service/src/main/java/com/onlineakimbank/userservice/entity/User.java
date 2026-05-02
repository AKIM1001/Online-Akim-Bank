package com.onlineakimbank.userservice.entity;

import com.onlineakimbank.userservice.entity.enums.Role;
import com.onlineakimbank.userservice.entity.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {

    @Id
    @Column(name = "user_id", length = 36, nullable = false)
    private String userId;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @ElementCollection
    @CollectionTable(
            name = "user_accounts",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "account_id")
    private List<String> accountIds = new ArrayList<>();

    private String accountState;
    private String accountStatus;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String address;

    @NotBlank
    @Column(nullable = false)
    private String hashPassword;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    @PrePersist
    protected void prePersist() {
        if (userId == null) {
            userId = UUID.randomUUID().toString();
        }
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        updatedDate = LocalDateTime.now();
    }

    public User(String email,
                String phoneNumber,
                String address,
                String hashPassword,
                Role role,
                UserType userType) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.hashPassword = hashPassword;
        this.role = role;
        this.userType = userType;
    }
}