package com.example.CardScanner.card.dto;

import com.example.CardScanner.card.entity.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardStatusRequest {
    Long id;
    CardStatus status;
}
