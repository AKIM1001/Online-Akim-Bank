package com.onlineakimbank.cardservice.controller;

import com.onlineakimbank.cardservice.dto.VirtualCardDto;
import com.onlineakimbank.cardservice.entity.Card;
import com.onlineakimbank.cardservice.request.VirtualCardRequest;
import com.onlineakimbank.cardservice.service.VirtualCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/card/virtual")
public class VirtualCardController extends AbstractController<VirtualCardDto, Card, VirtualCardRequest> {

    @Autowired
    public VirtualCardController(VirtualCardService service) { super(service); }
}

