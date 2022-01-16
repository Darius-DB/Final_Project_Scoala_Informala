package com.example.demo.exception;

public class DestinationNotFoundException extends RuntimeException{
    public DestinationNotFoundException(String message) {
        super(message);
    }
}
