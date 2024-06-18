package com.codecool.solarwatch.exception;

public class InvalidDateException extends RuntimeException{
    public InvalidDateException() {
        super("Date cannot be in the past");
    }
}
