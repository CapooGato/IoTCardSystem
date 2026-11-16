package com.example.CardScanner.card.service;

import com.example.CardScanner.card.dto.CardAccessLevelRequest;
import com.example.CardScanner.card.dto.CardDTO;
import com.example.CardScanner.card.dto.CardStatusRequest;
import com.example.CardScanner.card.entity.Card;
import com.example.CardScanner.card.entity.CardAccessLevel;
import com.example.CardScanner.card.entity.CardStatus;
import com.example.CardScanner.card.mapper.CardMapper;
import com.example.CardScanner.shared.ResourceNotFoundException;
import com.example.CardScanner.card.repository.CardRepository;
import com.example.CardScanner.user.entity.User;
import com.example.CardScanner.user.repository.UserRepository;
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

    public CardDTO saveCard(CardAccessLevelRequest request){
        Card card = generateCard(request.getAccessLevel());
        cardRepository.save(card);
        return CardMapper.toDto(card);
    }

    private Card generateCard(CardAccessLevel accessLevel){
       return Card.builder()
                .cardUID(UUID.randomUUID())
                .status(CardStatus.INACTIVE)
                .accessLevel(accessLevel)
                .validTo(LocalDateTime.now(ZoneId.systemDefault()).plusMonths(6))
                .build();
    }
    public CardDTO getCardById(Long id){
        Card card = cardRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Card id not found."));
        return CardMapper.toDto(card);
    }

    public void setUserToCard(Long cardId, Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User id not found"));
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card id not found."));
        card.setUser(user);
        cardRepository.save(card);
    }

    public List<CardDTO> getCards(){
        return cardRepository.findAll().stream()
                .map(CardMapper::toDto)
                .toList();
    }

    public void deleteCardById(Long id){
        cardRepository.deleteById(id);
    }

    public CardDTO updateValidToById(Long id){
        Card card  = cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card id not found"));
        card.setValidTo(LocalDateTime.now(ZoneId.systemDefault()).plusMonths(6));
        return CardMapper.toDto(cardRepository.save(card));
    }

    public CardDTO updateCardStatus(CardStatusRequest request){
        Card card  = cardRepository.findById(request.getId()).orElseThrow(() -> new ResourceNotFoundException("Card id not found"));
        card.setStatus(request.getStatus());
        return CardMapper.toDto(cardRepository.save(card));
    }
}
