package com.ssa.team3.backend.model.domain.IAM;

import com.ssa.team3.backend.model.domain.IAM.exceptions.InvalidCredentialsException;
import com.ssa.team3.backend.model.domain.IAM.exceptions.UserAlreadyExistsException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import java.util.Date;
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
    public void logout(UUID sessionId) {
        removeSession(sessionId);
    }

    @Override
    public void register(String username, String password, String firstName, String lastName) throws UserAlreadyExistsException, InvalidCredentialsException {
        Optional<User> userOptional = userRepository.getUserByUsername(username);
        if (userOptional.isPresent()){
            throw new UserAlreadyExistsException();
        }

        try {
            userRepository.insertUser(username, PasswordHashUtils.createHash(password), firstName, lastName);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InvalidCredentialsException();
        }
    }

    @Override
    public void renewSession(UUID sessionId) {
        Optional<Session> sessionOptional = sessionRepository.getSession(sessionId);
        if (sessionOptional.isEmpty()){
            return;
        }

        // From : https://stackoverflow.com/a/1005550
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 1);
        Date newExpiryDate = c.getTime();

        sessionRepository.setSessionExpiryDate(sessionId, newExpiryDate);
    }

    @Override
    public Optional<Session> getSession(UUID sessionId) {
        return sessionRepository.getSession(sessionId);
    }

    @Override
    public void removeSession(UUID sessionId) {
        sessionRepository.deleteSession(sessionId);
    }

    @Override
    public Optional<User> getUser(UUID userId) {
        return userRepository.getUserById(userId);
    }
}
