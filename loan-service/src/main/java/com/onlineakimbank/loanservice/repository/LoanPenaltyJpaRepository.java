package com.onlineakimbank.loanservice.repository;

import com.onlineakimbank.loanservice.entity.LoanPenalty;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanPenaltyJpaRepository extends LoanBaseJpaRepository<LoanPenalty, String> {

    List<LoanPenalty> findByLoanId(String loanId);

}