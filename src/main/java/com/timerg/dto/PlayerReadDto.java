package com.timerg.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.UniqueElements;

@Value
@Builder
public class PlayerReadDto {
    Long id;
    String name;
}
