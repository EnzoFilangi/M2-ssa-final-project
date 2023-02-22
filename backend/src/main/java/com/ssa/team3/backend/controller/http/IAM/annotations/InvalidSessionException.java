package com.ssa.team3.backend.controller.http.IAM.annotations;

public class InvalidSessionException extends Exception {
    // Parameterless Constructor
    public InvalidSessionException() {}

    // Constructor that accepts a message
    public InvalidSessionException(String message)
    {
        super(message);
    }
}
