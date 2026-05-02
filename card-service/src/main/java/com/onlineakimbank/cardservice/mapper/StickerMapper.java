package com.onlineakimbank.cardservice.mapper;

import com.onlineakimbank.cardservice.dto.StickerDto;
import com.onlineakimbank.cardservice.entity.Card;
import com.onlineakimbank.cardservice.entity.main.Sticker;
import com.onlineakimbank.cardservice.request.StickerRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StickerMapper extends MapperContract<StickerDto, Card, StickerRequest> {
    @Override
    StickerDto toDto(Card card);

    @Override
    Sticker fromRequest(StickerRequest stickerRequest);

    @Override
    Sticker fromDto(StickerDto stickerDto);
}
