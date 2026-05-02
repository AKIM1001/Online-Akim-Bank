package com.onlineakimbank.cardservice.mapper;

import com.onlineakimbank.cardservice.dto.LoanCardDto;
import com.onlineakimbank.cardservice.entity.Card;
import com.onlineakimbank.cardservice.request.LoanCardRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanCardMapper extends MapperContract<LoanCardDto, Card, LoanCardRequest> {

    @Override
    LoanCardDto toDto(Card card);

    @Override
    Card fromRequest(LoanCardRequest request);

    @Override
    Card fromDto(LoanCardDto dto);
}
