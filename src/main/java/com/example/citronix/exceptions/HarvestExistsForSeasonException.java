package com.example.citronix.exceptions;

public class HarvestExistsForSeasonException extends RuntimeException {
    public HarvestExistsForSeasonException(String message) {
        super(message);
    }
}
