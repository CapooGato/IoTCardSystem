package com.example.CardScanner.card.service;

import com.example.CardScanner.card.dto.CardAccessLevelRequest;
import com.example.CardScanner.card.dto.CardStatusRequest;
import com.example.CardScanner.card.entity.Card;
import com.example.CardScanner.card.entity.CardStatus;
import com.example.CardScanner.card.exception.ResourceNotFoundException;
import com.example.CardScanner.card.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card saveCard(CardAccessLevelRequest cardAccessLevelRequest){
        Card card = Card.builder()
                .cardUID(UUID.randomUUID())
                .status(CardStatus.ACTIVE)
                .accessLevel(cardAccessLevelRequest.getAccessLevel())
                .validTo(LocalDateTime.now(ZoneId.systemDefault()).plusMonths(6))
                .build();
        return cardRepository.save(card);
    }

    public Card getCardById(Long id){
        return cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card id not found"));
    }

    public List<Card> getCards(){
        return cardRepository.findAll();
    }

    public void deleteCardById(Long id){
        cardRepository.deleteById(id);
    }

    public Card updateValidToById(Long id){
        Card card  = cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card id not found"));
        card.setValidTo(LocalDateTime.now(ZoneId.systemDefault()).plusMonths(6));
        return cardRepository.save(card);
    }

    public Card updateCardStatus(CardStatusRequest cardStatusRequest){
        Card card  = cardRepository.findById(cardStatusRequest.getId()).orElseThrow(() -> new ResourceNotFoundException("Card id not found"));
        card.setStatus(cardStatusRequest.getStatus());
        return cardRepository.save(card);
    }
}
