package com.onlineakimbank.cardservice.mapper;

import com.onlineakimbank.cardservice.dto.RealCardDto;
import com.onlineakimbank.cardservice.entity.Card;
import com.onlineakimbank.cardservice.request.RealCardRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RealCardMapper
        extends MapperContract<RealCardDto, Card, RealCardRequest> {

    @Override
    RealCardDto toDto(Card card);

    @Override
    Card fromRequest(RealCardRequest request);

    @Override
    Card fromDto(RealCardDto dto);
}

