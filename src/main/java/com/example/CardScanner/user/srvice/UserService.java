package com.example.CardScanner.user.srvice;

import com.example.CardScanner.card.dto.CardAccessLevelRequest;
import com.example.CardScanner.card.dto.CardDTO;
import com.example.CardScanner.card.entity.Card;
import com.example.CardScanner.card.entity.CardStatus;
import com.example.CardScanner.card.exception.CardNotActiveException;
import com.example.CardScanner.shared.ResourceNotFoundException;
import com.example.CardScanner.card.service.CardService;
import com.example.CardScanner.user.dto.UserCreateRequest;
import com.example.CardScanner.user.dto.UserDTO;
import com.example.CardScanner.user.entity.User;
import com.example.CardScanner.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CardService cardService;

    @Autowired
    public UserService(UserRepository userRepository, CardService cardService) {
        this.userRepository = userRepository;
        this.cardService = cardService;
    }

    public User saveUser(UserCreateRequest request){
        User user = User.builder()
                .login(request.getLogin())
                .password(request.getPassword())
                .build();
        return userRepository.save(user);
    }

//    public UserDTO createCardForUser(Long id, CardAccessLevelRequest cardAccessLevelRequest){
//        User user = userRepository.findById(id).orElseThrow(
//                () -> new ResourceNotFoundException("User id not found"));
//        Card card = cardService.createCard(cardAccessLevelRequest.getAccessLevel());
//        cardService.setUserToCard(card.getId(), user.getId());
//
//        CardDTO cardDTO = CardDTO.builder()
//                .id(card.getId())
//                .cardUID(card.getCardUID())
//                .userId(user.getId())
//                .status(card.getStatus())
//                .accessLevel(card.getAccessLevel())
//                .validTo(card.getValidTo())
//                .build();
//
//        return UserDTO.builder()
//                .id(user.getId())
//                .login(user.getLogin())
//                .password(user.getPassword())
//                .cardDTO(cardDTO)
//                .build();
//    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User id not found"));
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

}
