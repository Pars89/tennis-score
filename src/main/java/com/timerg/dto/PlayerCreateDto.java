package com.timerg.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PlayerCreateDto {
    String name;
}
