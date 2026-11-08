package com.example.CardScanner.user.controller;

import com.example.CardScanner.card.dto.CardAccessLevelRequest;
import com.example.CardScanner.card.entity.Card;
import com.example.CardScanner.card.service.CardService;
import com.example.CardScanner.user.dto.UserCreateRequest;
import com.example.CardScanner.user.dto.UserDTO;
import com.example.CardScanner.user.entity.User;
import com.example.CardScanner.user.srvice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        return new ResponseEntity<>(userService.saveUser(userCreateRequest), HttpStatus.CREATED);
    }
    //TODO: Make setting card to user
//    @PostMapping("/{id}/card/{cardId}")
//    public ResponseEntity<UserDTO> addCardToUser(@PathVariable("id") Long id,
//                                                 @PathVariable("cardId") Long cardId) {
//        return new ResponseEntity<>(userService.createCardForUser(id, cardId), HttpStatus.OK);
//    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
