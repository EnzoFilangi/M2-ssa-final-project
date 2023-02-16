package com.ssa.team3.backend.model.domain.IAM.exceptions;

public class InvalidCredentialsException extends Exception {
    // Parameterless Constructor
    public InvalidCredentialsException() {}

    // Constructor that accepts a message
    public InvalidCredentialsException(String message)
    {
        super(message);
    }
}
