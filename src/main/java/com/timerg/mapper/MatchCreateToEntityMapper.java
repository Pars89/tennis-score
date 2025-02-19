package com.timerg.mapper;

import com.timerg.dto.MatchCreateDto;
import com.timerg.entity.MatchEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchCreateToEntityMapper implements Mapper<MatchCreateDto, MatchEntity>{

    private static final MatchCreateToEntityMapper INSTANCE = new MatchCreateToEntityMapper();
    private static final PlayerReadToEntityMapper playerReadToEntityMapper = PlayerReadToEntityMapper.getInstance();

    @Override
    public MatchEntity from(MatchCreateDto entity) {
        return MatchEntity.builder()
                .firstPlayerEntity(playerReadToEntityMapper.from(entity.getFirstPlayerReadDto()))
                .secondPlayerEntity(playerReadToEntityMapper.from(entity.getSecondPlayerReadDto()))
                .winner(playerReadToEntityMapper.from(entity.getWinnerPlayerReadDto()))
                .build();
    }

    public static MatchCreateToEntityMapper getInstance() {
        return INSTANCE;
    }
}
