package com.timerg.mapper;

import com.timerg.dto.PlayerReadDto;
import com.timerg.entity.PlayerEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerReadToEntityMapper implements Mapper<PlayerReadDto, PlayerEntity>{

    private static final PlayerReadToEntityMapper INSTANCE = new PlayerReadToEntityMapper();
    @Override
    public PlayerEntity from(PlayerReadDto entity) {
        return PlayerEntity.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static PlayerReadToEntityMapper getInstance() {
        return INSTANCE;
    }
}
