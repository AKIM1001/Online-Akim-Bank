package com.onlineakimbank.paymentservice.config;

import com.onlineakimbank.paymentservice.entity.enums.*;

public class PaymentValidationConfig {

    public static void validateBusinessRules(
            PayerType payerType,
            PaymentMethod method,
            PaymentType type
    ) {

        if (payerType == PayerType.BUSINESS && method == PaymentMethod.CARD) {
            throw new IllegalStateException("[BUSINESS cannot pay by CARD]");
        }

        if (payerType == PayerType.BUSINESS && type != PaymentType.BUSINESS_PAYMENT) {
            throw new IllegalStateException("[BUSINESS must use BUSINESS_PAYMENT]");
        }

        if (payerType == PayerType.INDIVIDUAL && type == PaymentType.BUSINESS_PAYMENT) {
            throw new IllegalStateException("[INDIVIDUAL cannot create BUSINESS_PAYMENT]");
        }

        if (payerType == PayerType.BUSINESS
                && type == PaymentType.SERVICE_INDIVIDUAL_PAYMENT) {
            throw new IllegalStateException("[BUSINESS cannot create SERVICE_INDIVIDUAL_PAYMENT]");
        }
    }


    public static void validateAccountState(State state) {

        if (state == State.CLOSED) {
            throw new IllegalStateException("[Account CLOSED]");
        }

        if (state == State.FROZEN) {
            throw new IllegalStateException("[Account FROZEN]");
        }

        if (state == State.BLOCKED) {
            throw new IllegalStateException("[Account BLOCKED]");
        }

        if (state != State.ACTIVE) {
            throw new IllegalStateException("[Account not ACTIVE]");
        }
    }


    public static void validatePrimeRestrictions(Status status, PaymentType type) {

        if (status == Status.NON_PRIME
                && type == PaymentType.SERVICE_INDIVIDUAL_PAYMENT) {
            // пока не запрещаем
            // добавить ограничения позже
        }
    }


    public static void validatePaymentStatusTransition(
            PaymentStatus current,
            PaymentStatus next
    ) {

        switch (current) {

            case CREATED -> {
                if (next != PaymentStatus.VALIDATED
                        && next != PaymentStatus.REJECTED) {
                    throw new IllegalStateException("[Invalid transition from CREATED]");
                }
            }

            case VALIDATED -> {
                if (next != PaymentStatus.SENT) {
                    throw new IllegalStateException("[Invalid transition from VALIDATED]");
                }
            }

            case SENT -> {
                if (next != PaymentStatus.PROCESSING
                        && next != PaymentStatus.FAILED) {
                    throw new IllegalStateException("[Invalid transition from SENT]");
                }
            }

            case PROCESSING -> {
                if (next != PaymentStatus.COMPLETED
                        && next != PaymentStatus.FAILED) {
                    throw new IllegalStateException("[Invalid transition from PROCESSING]");
                }
            }

            case COMPLETED, FAILED, REJECTED -> {
                throw new IllegalStateException("[Final state reached]");
            }
        }
    }
}