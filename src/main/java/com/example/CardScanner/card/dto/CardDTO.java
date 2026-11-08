package com.example.CardScanner.card.dto;

import com.example.CardScanner.card.entity.CardAccessLevel;
import com.example.CardScanner.card.entity.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDTO {
    private Long id;
    private UUID cardUID;
    private Long userId;
    private CardStatus status;
    private CardAccessLevel accessLevel;
    private LocalDateTime validTo;
}
