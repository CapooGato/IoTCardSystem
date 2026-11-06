package com.example.CardScanner.card.dto;

import com.example.CardScanner.card.entity.CardAccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardAccessLevelRequest {
    private CardAccessLevel accessLevel;
}
