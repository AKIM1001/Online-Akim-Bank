package com.onlineakimbank.loanservice.repository;

import com.onlineakimbank.loanservice.entity.LoanProduct;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanProductJpaRepository extends LoanBaseJpaRepository<LoanProduct, String> {
}
