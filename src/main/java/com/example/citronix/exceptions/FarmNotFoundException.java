package com.example.citronix.exceptions;

public class FarmNotFoundException extends RuntimeException {
    public FarmNotFoundException(String message) {
        super(message);
    }
}
