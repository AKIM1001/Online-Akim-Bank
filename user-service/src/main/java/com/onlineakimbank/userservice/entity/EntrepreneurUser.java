package com.onlineakimbank.userservice.entity;

import com.onlineakimbank.userservice.entity.enums.Role;
import com.onlineakimbank.userservice.entity.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "entrepreneur_user")
@PrimaryKeyJoinColumn(name = "user_id")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntrepreneurUser extends User {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Pattern(regexp = "^\\d{10}$")
    @Column(length = 10, nullable = false)
    private String inn;

    public EntrepreneurUser(String email,
                            String phoneNumber,
                            String address,
                            String hashPassword,
                            Role role,
                            UserType userType,
                            String firstName,
                            String lastName,
                            String inn) {

        super(email, phoneNumber, address, hashPassword, role, userType);
        this.firstName = firstName;
        this.lastName = lastName;
        this.inn = inn;
    }
}