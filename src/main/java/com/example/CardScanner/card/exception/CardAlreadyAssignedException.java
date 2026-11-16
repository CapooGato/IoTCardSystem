package com.example.CardScanner.card.exception;

public class CardAlreadyAssignedException extends RuntimeException {
    public CardAlreadyAssignedException(String message) {
        super(message);
    }
}
