package com.example.demo.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom runtime exception for validation and business logic errors.
 */
public class BadRequestException extends RuntimeException {

    private final HttpStatus status;

    public BadRequestException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public BadRequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    // Static helpers to enforce required error keywords
    public static BadRequestException duplicateEventCode() {
        return new BadRequestException("Event code already exists", HttpStatus.BAD_REQUEST);
    }

    public static BadRequestException invalidBasePrice() {
        return new BadRequestException("Base price must be > 0", HttpStatus.BAD_REQUEST);
    }

    public static BadRequestException invalidInventory() {
        return new BadRequestException("Remaining seats cannot exceed total seats", HttpStatus.BAD_REQUEST);
    }

    public static BadRequestException invalidMultiplier() {
        return new BadRequestException("Price multiplier must be > 0", HttpStatus.BAD_REQUEST);
    }

    public static BadRequestException missingInventory() {
        return new BadRequestException("Seat inventory not found", HttpStatus.NOT_FOUND);
    }

    public static BadRequestException inactiveEvent() {
        return new BadRequestException("Event is not active", HttpStatus.BAD_REQUEST);
    }
}
