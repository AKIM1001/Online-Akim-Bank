package com.onlineakimbank.cardservice.mapper;

import com.onlineakimbank.cardservice.dto.VirtualCardDto;
import com.onlineakimbank.cardservice.entity.Card;
import com.onlineakimbank.cardservice.entity.main.VirtualCard;
import com.onlineakimbank.cardservice.request.VirtualCardRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VirtualCardMapper extends MapperContract<VirtualCardDto, Card, VirtualCardRequest> {
    @Override
    VirtualCardDto toDto(Card card);

    @Override
    VirtualCard fromRequest(VirtualCardRequest virtualCardRequest);

    @Override
    VirtualCard fromDto(VirtualCardDto virtualCardDto);
}