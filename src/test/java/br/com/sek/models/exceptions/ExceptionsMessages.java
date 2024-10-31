package br.com.sek.models.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor

public enum ExceptionsMessages {
    BAD_REQUEST(400, "Expected status code 400 but got %d"),
    NOT_FOUND(404, "Expected status code 404 but got %d"),
    CONFLICT(409, "Expected status code 409 but got %d");

    private final int code;
    private final String message;

    public static ExceptionsMessages fromCode(int code) throws IllegalArgumentException{
        return Arrays.stream(ExceptionsMessages.values())
                .filter(type -> type.getCode() == code)
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }

}
