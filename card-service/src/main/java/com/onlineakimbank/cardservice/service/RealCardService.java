package com.onlineakimbank.cardservice.service;

import com.onlineakimbank.cardservice.dto.RealCardDto;
import com.onlineakimbank.cardservice.entity.main.RealCard;
import com.onlineakimbank.cardservice.mapper.RealCardMapper;
import com.onlineakimbank.cardservice.repository.CardJpaRepository;
import com.onlineakimbank.cardservice.request.RealCardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealCardService extends AbstractService<RealCardDto, RealCard, RealCardRequest> {
    @Autowired
    public RealCardService(CardJpaRepository cardJpaRepository,
                           RealCardMapper realCardMapper) {
        super(cardJpaRepository, realCardMapper);
    }
}
