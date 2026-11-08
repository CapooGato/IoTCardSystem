package com.example.CardScanner.card.service;

import com.example.CardScanner.card.dto.CardAccessLevelRequest;
import com.example.CardScanner.card.dto.CardDTO;
import com.example.CardScanner.card.dto.CardStatusRequest;
import com.example.CardScanner.card.entity.Card;
import com.example.CardScanner.card.entity.CardAccessLevel;
import com.example.CardScanner.card.entity.CardStatus;
import com.example.CardScanner.shared.ResourceNotFoundException;
import com.example.CardScanner.card.repository.CardRepository;
import com.example.CardScanner.user.entity.User;
import com.example.CardScanner.user.repository.UserRepository;
import com.example.CardScanner.user.srvice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Autowired
    public CardService(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public Card saveCard(CardAccessLevelRequest request){
        Card card = createCard(request.getAccessLevel());
        return cardRepository.save(card);
    }

    public Card createCard(CardAccessLevel accessLevel){
       return Card.builder()
                .cardUID(UUID.randomUUID())
                .status(CardStatus.ACTIVE)
                .accessLevel(accessLevel)
                .validTo(LocalDateTime.now(ZoneId.systemDefault()).plusMonths(6))
                .build();
    }
    public Card getCardById(Long id){
        return cardRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Card id not found"));
    }

    public void setUserToCard(Long cardId, Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User id not found"));;
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card id not found"));
        card.setUser(user);
        cardRepository.save(card);
    }

    public CardDTO setUserIdToCardDTO(Long cardId, Long userId){
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new ResourceNotFoundException("Card id not found"));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Card id not found"));;

        return CardDTO.builder()
                .id(card.getId())
                .cardUID(card.getCardUID())
                .userId(user.getId())
                .accessLevel(card.getAccessLevel())
                .status(card.getStatus())
                .validTo(card.getValidTo())
                .build();
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

    public Card updateCardStatus(CardStatusRequest request){
        Card card  = cardRepository.findById(request.getId()).orElseThrow(() -> new ResourceNotFoundException("Card id not found"));
        card.setStatus(request.getStatus());
        return cardRepository.save(card);
    }
}
