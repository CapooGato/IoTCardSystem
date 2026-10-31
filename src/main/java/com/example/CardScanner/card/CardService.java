package com.example.CardScanner.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card saveCard(CardDTO CardDTO){
        Card card = Card.builder()
                .cardUID(UUID.randomUUID())
                .status(CardStatus.ACTIVE)
                .accessLevel(CardDTO.getAccessLevel())
                .validTo(LocalDateTime.now().plusMonths(6))
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

    public Card prolongValidToById(Long id){
        Card card  = cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card id not found"));
        card.setValidTo(LocalDateTime.now().plusMonths(6));
        return card;
    }
}
