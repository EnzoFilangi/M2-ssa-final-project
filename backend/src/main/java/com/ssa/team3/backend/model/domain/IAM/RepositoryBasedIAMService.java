package com.ssa.team3.backend.model.domain.IAM;

import com.ssa.team3.backend.model.domain.IAM.exceptions.InvalidCredentialsException;
import com.ssa.team3.backend.model.domain.IAM.exceptions.UserAlreadyExistsException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class RepositoryBasedIAMService implements IAMService {
    @Inject
    UserRepository userRepository;

    @Inject
    SessionRepository sessionRepository;

    @Override
    public Session login(String username, String password) throws InvalidCredentialsException {
        Optional<User> userOptional = userRepository.getUserByUsername(username);
        if (userOptional.isEmpty()){
            throw new InvalidCredentialsException();
        }

        try {
            if (!PasswordHashUtils.validatePassword(password, userOptional.get().getPassword())){
                throw new InvalidCredentialsException();
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InvalidCredentialsException();
        }

        return sessionRepository.insertSession(userOptional.get().getId());
    }

    @Override
    public void register(String username, String password) throws UserAlreadyExistsException, InvalidCredentialsException {
        Optional<User> userOptional = userRepository.getUserByUsername(username);
        if (userOptional.isPresent()){
            throw new UserAlreadyExistsException();
        }

        try {
            userRepository.insertUser(username, PasswordHashUtils.createHash(password));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InvalidCredentialsException();
        }
    }

    @Override
    public Optional<Session> getSession(UUID sessionId) {
        return sessionRepository.getSession(sessionId);
    }
}
