package com.example.CardScanner.card.mapper;

import com.example.CardScanner.card.dto.CardDTO;
import com.example.CardScanner.card.entity.Card;

public class CardMapper {

    private CardMapper() {
        /* This class should not be initialised */
    }
    public static CardDTO toDto(Card card){
        return CardDTO.builder()
                .id(card.getId())
                .cardUID(card.getCardUID())
                .userId(card.getUser() != null ? card.getUser().getId() : null)
                .status(card.getStatus())
                .accessLevel(card.getAccessLevel())
                .validTo(card.getValidTo())
                .build();
    }

    public static Card toEntity(CardDTO cardDTO){
        return Card.builder()
                .id(cardDTO.getId())
                .cardUID(cardDTO.getCardUID())
                .status(cardDTO.getStatus())
                .accessLevel(cardDTO.getAccessLevel())
                .validTo(cardDTO.getValidTo())
                .build();
    }
}
