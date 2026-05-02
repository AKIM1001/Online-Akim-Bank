package com.onlineakimbank.loanservice.repository;

import com.onlineakimbank.loanservice.entity.LoanApplication;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanApplicationJpaRepository  extends LoanBaseJpaRepository<LoanApplication, String>{
}
