package com.onlineakimbank.cardservice.service;

import com.onlineakimbank.cardservice.dto.StickerDto;
import com.onlineakimbank.cardservice.entity.main.Sticker;
import com.onlineakimbank.cardservice.mapper.StickerMapper;
import com.onlineakimbank.cardservice.repository.CardJpaRepository;
import com.onlineakimbank.cardservice.request.StickerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StickerService extends AbstractService<StickerDto, Sticker, StickerRequest> {
    @Autowired
    public StickerService(CardJpaRepository cardJpaRepository,
                           StickerMapper stickerMapper) {
        super(cardJpaRepository, stickerMapper);
    }
}