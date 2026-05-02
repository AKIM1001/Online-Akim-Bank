package com.onlineakimbank.loanservice.repository;

import com.onlineakimbank.loanservice.entity.enums.LoanType;
import com.onlineakimbank.loanservice.entity.enums.UserType;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRateJpaRepository
        extends JpaRepository<LoanRate, String> {

    Optional<LoanRate> findByLoanTypeAndUserType(
            LoanType loanType,
            UserType userType
    );
}