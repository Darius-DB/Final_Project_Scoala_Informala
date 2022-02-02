package com.example.demo.destination.exception;

public class DestinationNotFoundException extends RuntimeException{
    public DestinationNotFoundException(String message) {
        super(message);
    }
}
