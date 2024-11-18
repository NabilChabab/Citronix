package com.example.citronix.exceptions;

public class FarmExistsException extends RuntimeException {
    public FarmExistsException(String message) {
        super(message);
    }
}
