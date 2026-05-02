package com.onlineakimbank.cardservice.controller;

import com.onlineakimbank.cardservice.dto.StickerDto;
import com.onlineakimbank.cardservice.entity.Card;
import com.onlineakimbank.cardservice.request.StickerRequest;
import com.onlineakimbank.cardservice.service.StickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/card/sticker")
public class StickerController extends AbstractController<StickerDto, Card, StickerRequest> {

    @Autowired
    public StickerController(StickerService service) { super(service); }
}

