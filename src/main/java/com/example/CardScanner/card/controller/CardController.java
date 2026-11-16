package com.example.CardScanner.card.controller;

import com.example.CardScanner.card.dto.CardAccessLevelRequest;
import com.example.CardScanner.card.dto.CardDTO;
import com.example.CardScanner.card.dto.CardStatusRequest;
import com.example.CardScanner.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final CardService service;
    @Autowired
    public CardController(CardService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<CardDTO> createCard(@RequestBody CardAccessLevelRequest cardAccessLevelRequest) {
        return new ResponseEntity<>(service.saveCard(cardAccessLevelRequest), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<CardDTO>> getCards() {
        return new ResponseEntity<>(service.getCards(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.getCardById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardById(@PathVariable("id") Long id) {
        service.deleteCardById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/prolong")
    public ResponseEntity<CardDTO> updateCardValidationDateById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.updateValidToById(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<CardDTO> updateCardStatus(@PathVariable("id") Long id,
                                                 @RequestBody CardStatusRequest cardStatusRequest) {
        return new ResponseEntity<>(service.updateCardStatus(cardStatusRequest), HttpStatus.OK);
    }
}
