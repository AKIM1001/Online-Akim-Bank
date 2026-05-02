package com.onlineakimbank.accountservice.mapper;

import com.onlineakimbank.accountservice.dto.AccountDto;
import com.onlineakimbank.accountservice.entity.Account;
import com.onlineakimbank.accountservice.request.AccountRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto entityToDto(Account account);
    Account requestToEntity(AccountRequest accountRequest);
}
