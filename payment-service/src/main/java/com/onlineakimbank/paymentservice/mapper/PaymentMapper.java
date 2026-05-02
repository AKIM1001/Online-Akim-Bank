package com.onlineakimbank.paymentservice.mapper;

import com.onlineakimbank.paymentservice.dto.PaymentDto;
import com.onlineakimbank.paymentservice.entity.Payment;
import com.onlineakimbank.paymentservice.entity.enums.Currency;
import com.onlineakimbank.paymentservice.entity.enums.PayerType;
import com.onlineakimbank.paymentservice.entity.enums.PaymentStatus;
import com.onlineakimbank.paymentservice.entity.enums.UserType;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.time.Instant;

import java.time.Instant;

import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
public class PaymentMapper {

    public Payment toEntity(PaymentDto dto) {
        Payment payment = new Payment();
        payment.setPaymentId(dto.paymentId());
        payment.setAccountId(dto.accountId());
        payment.setPayerType(resolvePayerType(dto.userType()));
        payment.setPaymentMethod(dto.paymentMethod());
        payment.setPaymentType(dto.paymentType());
        payment.setAmount(dto.amount());
        payment.setCurrency(dto.currency()); // если Currency — enum, иначе Currency.valueOf(...)
        payment.setPaymentStatus(PaymentStatus.CREATED);
        payment.setCreatedAt(Instant.now());
        payment.setUpdatedAt(Instant.now());
        return payment;
    }

    private PayerType resolvePayerType(UserType userType) {
        return switch (userType) {
            case INDIVIDUAL -> PayerType.INDIVIDUAL;
            case ENTREPRENEUR, COMPANY -> PayerType.BUSINESS;
        };
    }
}