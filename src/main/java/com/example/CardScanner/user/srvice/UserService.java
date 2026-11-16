package com.example.CardScanner.user.srvice;

import com.example.CardScanner.card.entity.Card;
import com.example.CardScanner.card.entity.CardStatus;
import com.example.CardScanner.card.exception.CardAlreadyAssignedException;
import com.example.CardScanner.card.repository.CardRepository;
import com.example.CardScanner.shared.ResourceNotFoundException;
import com.example.CardScanner.user.dto.UserCreateRequest;
import com.example.CardScanner.user.dto.UserDTO;
import com.example.CardScanner.user.entity.User;
import com.example.CardScanner.user.mapper.UserMapper;
import com.example.CardScanner.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    @Autowired
    public UserService(UserRepository userRepository, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    public UserDTO saveUser(UserCreateRequest request){
        User user = User.builder()
                .login(request.getLogin())
                .password(request.getPassword())
                .build();
        return UserMapper.toDto(userRepository.save(user));
    }

    public UserDTO addCardForUser(Long id, Long cardId){
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User id not found."));

        if(user.getCard() != null){
            throw new CardAlreadyAssignedException("User already has a card.");
        }

        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new ResourceNotFoundException("Card id not found."));

        if(card.getStatus().equals(CardStatus.ACTIVE)){
            throw new CardAlreadyAssignedException("Card already active and in use.");
        }

        user.setCard(card);
        userRepository.save(user);

        return UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .cardId(card.getId())
                .build();
    }

    public UserDTO getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User id not found"));
        return UserMapper.toDto(user);
    }

    public List<UserDTO> getUsers(){
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

}
