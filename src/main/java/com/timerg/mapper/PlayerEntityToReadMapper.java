package com.timerg.mapper;

import com.timerg.dto.PlayerReadDto;
import com.timerg.entity.PlayerEntity;
import com.timerg.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerEntityToReadMapper implements Mapper<PlayerEntity, PlayerReadDto> {
    private static final PlayerEntityToReadMapper INSTANCE = new PlayerEntityToReadMapper();
    @Override
    public PlayerReadDto from(PlayerEntity entity) {
        return PlayerReadDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static PlayerEntityToReadMapper getInstance() {
        return INSTANCE;
    }
}
