package com.ssa.team3.backend.model.domain.IAM.exceptions;

public class UserAlreadyExistsException extends Exception {
    // Parameterless Constructor
    public UserAlreadyExistsException() {}

    // Constructor that accepts a message
    public UserAlreadyExistsException(String message)
    {
        super(message);
    }
}
