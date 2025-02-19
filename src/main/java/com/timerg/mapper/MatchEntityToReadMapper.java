package com.timerg.mapper;

import com.timerg.dto.MatchReadDto;
import com.timerg.entity.MatchEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchEntityToReadMapper implements Mapper<MatchEntity, MatchReadDto>{
    private static final MatchEntityToReadMapper INSTANCE = new MatchEntityToReadMapper();
    private final PlayerEntityToReadMapper playerEntityToReadMapper = PlayerEntityToReadMapper.getInstance();
    @Override
    public MatchReadDto from(MatchEntity entity) {
        return MatchReadDto.builder()
                .id(entity.getId())
                .firstPlayerReadDto(playerEntityToReadMapper.from(entity.getFirstPlayerEntity()))
                .secondPlayerReadDto(playerEntityToReadMapper.from(entity.getSecondPlayerEntity()))
                .winnerPlayerReadDto(playerEntityToReadMapper.from(entity.getWinner()))
                .build();
    }

    public static MatchEntityToReadMapper getInstance() {
        return INSTANCE;
    }
}
