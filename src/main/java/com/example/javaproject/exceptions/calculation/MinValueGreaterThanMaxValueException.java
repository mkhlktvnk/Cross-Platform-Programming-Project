package com.example.javaproject.exceptions.calculation;

public class MinValueGreaterThanMaxValueException extends RuntimeException {
    public MinValueGreaterThanMaxValueException(String message) {
        super(message);
    }
}
