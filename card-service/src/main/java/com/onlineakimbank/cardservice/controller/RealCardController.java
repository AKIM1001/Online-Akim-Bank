package com.onlineakimbank.cardservice.controller;

import com.onlineakimbank.cardservice.dto.RealCardDto;
import com.onlineakimbank.cardservice.entity.Card;
import com.onlineakimbank.cardservice.request.RealCardRequest;
import com.onlineakimbank.cardservice.service.RealCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/card/real")
public class RealCardController extends AbstractController<RealCardDto, Card, RealCardRequest> {

    @Autowired
    public RealCardController(RealCardService service) { super(service); }
}
