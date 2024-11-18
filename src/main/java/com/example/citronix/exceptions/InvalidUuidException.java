package com.example.citronix.exceptions;

public class InvalidUuidException extends RuntimeException {
    public InvalidUuidException(String message) {
        super(message);
    }
}
