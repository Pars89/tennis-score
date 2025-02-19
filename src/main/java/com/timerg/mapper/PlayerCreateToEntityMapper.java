package com.timerg.mapper;

import com.timerg.dto.PlayerCreateDto;
import com.timerg.entity.PlayerEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerCreateToEntityMapper implements Mapper<PlayerCreateDto, PlayerEntity>{

    private static final PlayerCreateToEntityMapper INSTANCE = new PlayerCreateToEntityMapper();

    @Override
    public PlayerEntity from(PlayerCreateDto entity) {
        return PlayerEntity.builder()
                .name(entity.getName())
                .build();
    }

    public static PlayerCreateToEntityMapper getInstance() {
        return INSTANCE;
    }
}
