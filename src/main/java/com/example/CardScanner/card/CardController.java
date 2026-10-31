package com.example.CardScanner.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/card")
public class CardController {
    private final CardService service;
    @Autowired
    public CardController(CardService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<Card> saveCard(CardDTO CardDTO) {
        return new ResponseEntity<>(service.saveCard(CardDTO), HttpStatus.CREATED);
    }
}
