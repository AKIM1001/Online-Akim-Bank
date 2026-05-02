package com.onlineakimbank.userservice.service;

import com.onlineakimbank.userservice.dto.UserDto;
import com.onlineakimbank.userservice.entity.CompanyUser;
import com.onlineakimbank.userservice.entity.EntrepreneurUser;
import com.onlineakimbank.userservice.entity.IndividualUser;
import com.onlineakimbank.userservice.entity.User;
import com.onlineakimbank.userservice.entity.enums.Role;
import com.onlineakimbank.userservice.exception.NoSuchUserException;
import com.onlineakimbank.userservice.exception.UserAlreadyExistsException;
import com.onlineakimbank.userservice.mapper.UserMapper;
import com.onlineakimbank.userservice.repository.UserJpaRepository;
import com.onlineakimbank.userservice.request.CompanyUserRequest;
import com.onlineakimbank.userservice.request.EntrepreneurUserRequest;
import com.onlineakimbank.userservice.request.IndividualUserRequest;
import com.onlineakimbank.userservice.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto registerUserWithRole(UserRequest request) {
        if (userJpaRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("[User with email " + request.email() + " already exists]");
        }

        String hash = passwordEncoder.encode(request.password());

        User userEntity = switch (request.userType()) {

            case INDIVIDUAL -> {
                var r = (IndividualUserRequest) request;
                yield new IndividualUser(
                        r.email(), r.phoneNumber(), r.address(), hash,
                        r.role(), r.userType(),
                        r.firstName(), r.lastName(), r.passportSeries(),
                        r.passportNumber(), r.birthday()
                );
            }

            case ENTREPRENEUR -> {
                var r = (EntrepreneurUserRequest) request;
                yield new EntrepreneurUser(
                        r.email(), r.phoneNumber(), r.address(), hash,
                        r.role(), r.userType(),
                        r.firstName(), r.lastName(), r.inn()
                );
            }

            case COMPANY -> {
                var r = (CompanyUserRequest) request;
                yield new CompanyUser(
                        r.email(), r.phoneNumber(), r.address(), hash,
                        r.role(), r.userType(),
                        r.companyName(), r.inn(), r.kpp(), r.legalAddress()
                );
            }
        };

        userEntity = userJpaRepository.save(userEntity);
        return userMapper.entityToDto(userEntity);
    }

    public UserDto updateUser(String userId, UserRequest request, Role currentUserRole) {
        User existing = userJpaRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException("[User not found]"));

        if (request.email() != null) existing.setEmail(request.email());
        if (request.phoneNumber() != null) existing.setPhoneNumber(request.phoneNumber());
        if (request.address() != null) existing.setAddress(request.address());
        if (request.password() != null) existing.setHashPassword(passwordEncoder.encode(request.password()));

        if (request.userType() != null && currentUserRole != Role.ADMIN) {
            throw new IllegalStateException("[Only admin can change user type]");
        }
        if (request.userType() != null) existing.setUserType(request.userType());

        if (request.role() != null && currentUserRole != Role.ADMIN) {
            throw new IllegalStateException("[Only admin can change role]");
        }
        if (request.role() != null) existing.setRole(request.role());

        User saved = userJpaRepository.save(existing);
        return userMapper.entityToDto(saved);
    }

    public boolean existsById(String userId) {
        return userJpaRepository.existsById(userId);
    }

    public Page<UserDto> findAll(Pageable pageable) {
        return userJpaRepository.findAll(pageable).map(userMapper::entityToDto);
    }

    public UserDto findById(String userId) {
        return userJpaRepository.findById(userId)
                .map(userMapper::entityToDto)
                .orElseThrow(NoSuchUserException::new);
    }

    public UserDto findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userMapper::entityToDto)
                .orElseThrow(NoSuchUserException::new);
    }

    public void deleteById(String userId) {
        if (!userJpaRepository.existsById(userId)) throw new NoSuchUserException();
        userJpaRepository.deleteById(userId);
    }

    public void deleteByEmail(String email) {
        if (!userJpaRepository.existsByEmail(email)) throw new NoSuchUserException();
        userJpaRepository.deleteByEmail(email);
    }

    public void addAccountToUser(String userId, String accountId) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException("[User not found]"));

        if (!user.getAccountIds().contains(accountId)) {
            user.getAccountIds().add(accountId);
            userJpaRepository.save(user);
        }
    }

    public void updateAccountState(String userId, String accountId, String newState) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException("[User not found]"));

        if (!user.getAccountIds().contains(accountId)) {
            throw new IllegalStateException("[User does not have this account]");
        }

        user.setAccountState(newState);
        userJpaRepository.save(user);
    }

    public void updateAccountStatus(String userId, String accountId, String newStatus) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(NoSuchUserException::new);

        if (!user.getAccountIds().contains(accountId)) {
            throw new IllegalStateException("[User does not have this account]");
        }

        user.setAccountStatus(newStatus);
        userJpaRepository.save(user);
    }


}
