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
@Table(name = "company_user")
@PrimaryKeyJoinColumn(name = "user_id")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyUser extends User {

    @NotBlank
    private String companyName;

    @Pattern(regexp = "^\\d{10}$")
    @Column(length = 10, nullable = false)
    private String inn;

    @Pattern(regexp = "^\\d{9}$")
    @Column(length = 9, nullable = false)
    private String kpp;

    @NotBlank
    private String legalAddress;

    public CompanyUser(String email,
                       String phoneNumber,
                       String address,
                       String hashPassword,
                       Role role,
                       UserType userType,
                       String companyName,
                       String inn,
                       String kpp,
                       String legalAddress) {

        super(email, phoneNumber, address, hashPassword, role, userType);
        this.companyName = companyName;
        this.inn = inn;
        this.kpp = kpp;
        this.legalAddress = legalAddress;
    }
}