package com.timerg.validation;

import com.timerg.dto.PlayerCreateDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerNameValidator implements Validator<String> {
    private static final PlayerNameValidator INSTANCE = new PlayerNameValidator();
    private static final String MESSAGE_LENGTH_MAX_PLAYER = "Максимальная длина — 30 символов.";
    private static final String MESSAGE_LENGTH_MIN_PLAYER = " Поле не может быть пустым.";
    private static final String MESSAGE_TRIM_PLAYER = "Имя не может содержать только пробелы.";
    private static final String MESSAGE_INVALID_CHARACTERS = "Имя может содержать только буквы.";
    private static final String MESSAGE_NO_SPACES = "Имя не должно начинаться или заканчиваться пробелом.";

    @Override
    public ValidationResult isValid(String name) {
        ValidationResult result = new ValidationResult();

        checkLengthName(name, result);

        return result;
    }

    private void checkLengthName(String playerName, ValidationResult result) {

        if (playerName == null || playerName.trim().isEmpty()) {
            result.add(ErrorResponse.of("invalid.name", MESSAGE_LENGTH_MIN_PLAYER));
            return;
        }
        if (playerName.length() > 30) {
            result.add(ErrorResponse.of("invalid.name", MESSAGE_LENGTH_MAX_PLAYER));
        }

        if (playerName.trim().isEmpty()) {
            result.add(ErrorResponse.of("invalid.name", MESSAGE_TRIM_PLAYER));
        }
        if (!playerName.equals(playerName.trim())) {
            result.add(ErrorResponse.of("invalid.name", MESSAGE_NO_SPACES));
        }

        if (!playerName.matches("^[a-zA-Zа-яА-ЯёЁ]+$")) {
            result.add(ErrorResponse.of("invalid.name", MESSAGE_INVALID_CHARACTERS));
        }
    }

    public static PlayerNameValidator getInstance() {
        return INSTANCE;
    }
}
