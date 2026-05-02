package com.onlineakimbank.loanservice.repository;

import com.onlineakimbank.loanservice.entity.LoanDecision;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanDecisionRepository extends LoanBaseJpaRepository<LoanDecision, String> {

    Optional<LoanDecision> findByLoanApplicationId(String applicationId);

}
