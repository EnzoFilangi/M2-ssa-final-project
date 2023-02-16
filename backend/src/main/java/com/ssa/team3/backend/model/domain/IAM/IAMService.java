package com.ssa.team3.backend.model.domain.IAM;

import com.ssa.team3.backend.model.domain.IAM.exceptions.InvalidCredentialsException;
import com.ssa.team3.backend.model.domain.IAM.exceptions.UserAlreadyExistsException;

import java.util.Optional;
import java.util.UUID;

public interface IAMService {
    Session login(String username, String password) throws InvalidCredentialsException;

    void logout(UUID sessionId);

    void register(String username, String password) throws UserAlreadyExistsException, InvalidCredentialsException;

    Optional<Session> getSession(UUID sessionId);
}
