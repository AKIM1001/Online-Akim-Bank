package com.onlineakimbank.userservice.entity;

import com.onlineakimbank.userservice.entity.enums.Role;
import com.onlineakimbank.userservice.entity.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "individual_user")
@PrimaryKeyJoinColumn(name = "user_id")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualUser extends User {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Pattern(regexp = "^\\d{2}\\s?\\d{2}$")
    @Column(length = 4, nullable = false)
    private String passportSeries;

    @Pattern(regexp = "^\\d{6}$")
    @Column(length = 6, nullable = false)
    private String passportNumber;

    @NotNull
    private LocalDate birthday;

    public IndividualUser(String email,
                          String phoneNumber,
                          String address,
                          String hashPassword,
                          Role role,
                          UserType userType,
                          String firstName,
                          String lastName,
                          String passportSeries,
                          String passportNumber,
                          LocalDate birthday) {

        super(email, phoneNumber, address, hashPassword, role, userType);
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
        this.birthday = birthday;
    }
}