package com.example.rqchallenge.exception;

public class NoEmployeesFoundException extends RuntimeException {
    public NoEmployeesFoundException(String message) {
        super(message);
    }
}
