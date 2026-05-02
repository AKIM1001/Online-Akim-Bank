package com.onlineakimbank.loanservice.repository;

import com.onlineakimbank.loanservice.entity.LoanInstallment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanInstallmentJpaRepository extends LoanBaseJpaRepository<LoanInstallment, String> {

    List<LoanInstallment> findByLoanId(String loanId);

}
