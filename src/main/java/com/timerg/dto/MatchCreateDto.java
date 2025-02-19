package com.timerg.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MatchCreateDto {
    PlayerReadDto firstPlayerReadDto;
    PlayerReadDto secondPlayerReadDto;

    PlayerReadDto winnerPlayerReadDto;
}
