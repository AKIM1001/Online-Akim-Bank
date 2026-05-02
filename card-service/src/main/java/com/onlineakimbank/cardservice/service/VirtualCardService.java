package com.onlineakimbank.cardservice.service;

import com.onlineakimbank.cardservice.dto.VirtualCardDto;
import com.onlineakimbank.cardservice.entity.main.VirtualCard;
import com.onlineakimbank.cardservice.mapper.VirtualCardMapper;
import com.onlineakimbank.cardservice.repository.CardJpaRepository;
import com.onlineakimbank.cardservice.request.VirtualCardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VirtualCardService extends AbstractService<VirtualCardDto, VirtualCard, VirtualCardRequest> {
    @Autowired
    public VirtualCardService(CardJpaRepository cardJpaRepository,
                           VirtualCardMapper virtualCardMapper) {
        super(cardJpaRepository, virtualCardMapper);
    }
}
