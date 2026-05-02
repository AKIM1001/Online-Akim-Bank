package com.onlineakimbank.userservice.mapper;

import com.onlineakimbank.userservice.dto.CompanyUserDto;
import com.onlineakimbank.userservice.dto.EntrepreneurUserDto;
import com.onlineakimbank.userservice.dto.UserDto;
import com.onlineakimbank.userservice.dto.IndividualUserDto;
import com.onlineakimbank.userservice.entity.CompanyUser;
import com.onlineakimbank.userservice.entity.EntrepreneurUser;
import com.onlineakimbank.userservice.entity.User;
import com.onlineakimbank.userservice.entity.IndividualUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    default UserDto entityToDto(User user) {
        if (user instanceof IndividualUser individual) {
            return individualUserToDto(individual);
        }
        throw new IllegalArgumentException("Unknown user type: " + user.getClass());
    }

    default IndividualUserDto individualUserToDto(IndividualUser user) {
        return new IndividualUserDto(
                user.getUserId(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getHashPassword(),
                user.getRole(),
                user.getUserType(),
                user.getCreatedDate(),
                user.getUpdatedDate(),

                user.getFirstName(),
                user.getLastName(),
                user.getPassportSeries(),
                user.getPassportNumber(),
                user.getBirthday()
        );
    }

    default EntrepreneurUserDto entrepreneurUserToDto(EntrepreneurUser user) {
        return new EntrepreneurUserDto(
                user.getUserId(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getHashPassword(),
                user.getRole(),
                user.getUserType(),
                user.getCreatedDate(),
                user.getUpdatedDate(),
                user.getFirstName(),
                user.getLastName(),
                user.getInn()
        );
    }

    default CompanyUserDto companyUserToDto(CompanyUser user) {
        return new CompanyUserDto(
                user.getUserId(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getHashPassword(),
                user.getRole(),
                user.getUserType(),
                user.getCreatedDate(),
                user.getUpdatedDate(),
                user.getCompanyName(),
                user.getInn(),
                user.getKpp(),
                user.getLegalAddress()
        );
    }
}