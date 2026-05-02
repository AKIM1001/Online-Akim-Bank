package com.onlineakimbank.loanservice.repository;

import com.onlineakimbank.loanservice.entity.LoanCollateral;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanCollateralJpaRepository extends LoanBaseJpaRepository<LoanCollateral, String> {

    List<LoanCollateral> findByLoanId(String loanId);

}
