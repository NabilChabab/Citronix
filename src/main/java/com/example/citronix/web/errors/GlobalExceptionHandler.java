package com.example.citronix.web.errors;

import com.example.citronix.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String , String>> handleValidationExceptions(MethodArgumentNotValidException exception){
        Map<String , String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField() , error.getDefaultMessage()));
        return new ResponseEntity<>(errors , HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(FarmExistsException.class)
    public ResponseEntity<Map<String, String>> handleFarmExistsException(FarmExistsException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FarmNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleFarmNotFoundException(FarmNotFoundException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FarmSizeException.class)
    public ResponseEntity<Map<String, String>> handleFarmSizeException(FarmSizeException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FieldAreaException.class)
    public ResponseEntity<Map<String, String>> handleFieldAreaException(FieldAreaException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FieldNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleFieldNotFoundException(FieldNotFoundException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TreeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTreeNotFoundException(TreeNotFoundException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TreeHarvestedForSeason.class)
    public ResponseEntity<Map<String, String>> handleTreeHarvestedForSeason(TreeHarvestedForSeason exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HarvestExistsForSeasonException.class)
    public ResponseEntity<Map<String, String>> handleHarvestExistsForSeasonException(HarvestExistsForSeasonException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HarvestPlantingException.class)
    public ResponseEntity<Map<String, String>> handleHarvestPlantingException(HarvestPlantingException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidUuidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidUuidException(InvalidUuidException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

}
