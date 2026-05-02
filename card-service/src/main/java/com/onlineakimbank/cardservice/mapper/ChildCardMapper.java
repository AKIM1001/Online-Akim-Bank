package com.onlineakimbank.cardservice.mapper;

import com.onlineakimbank.cardservice.dto.ChildCardDto;
import com.onlineakimbank.cardservice.entity.Card;
import com.onlineakimbank.cardservice.request.ChildCardRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChildCardMapper extends MapperContract<ChildCardDto, Card, ChildCardRequest> {

    @Override
    ChildCardDto toDto(Card card);

    @Override
    Card fromRequest(ChildCardRequest request);

    @Override
    Card fromDto(ChildCardDto dto);
}
