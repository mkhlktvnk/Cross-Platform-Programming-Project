package com.example.javaproject.exceptions.calculation;

public class WrongArgumentsOrderException extends RuntimeException {
    public WrongArgumentsOrderException(String message) {
        super(message);
    }
}
