package com.timerg.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MatchReadDto {
    Long id;
    PlayerReadDto firstPlayerReadDto;
    PlayerReadDto secondPlayerReadDto;
    PlayerReadDto winnerPlayerReadDto;
}
