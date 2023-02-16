package com.ssa.team3.backend.model.domain.IAM;

import com.ssa.team3.backend.model.domain.IAM.exceptions.InvalidCredentialsException;
import com.ssa.team3.backend.model.domain.IAM.exceptions.UserAlreadyExistsException;

public interface IAMService {
    Session login(String username, String password) throws InvalidCredentialsException;

    void register(String username, String password) throws UserAlreadyExistsException, InvalidCredentialsException;
}
