package com.timerg.exception;

public class PlayerPlayingAgainstSelfException extends RuntimeException {
    public PlayerPlayingAgainstSelfException(String message) {
        super(message + " ");
    }
}
