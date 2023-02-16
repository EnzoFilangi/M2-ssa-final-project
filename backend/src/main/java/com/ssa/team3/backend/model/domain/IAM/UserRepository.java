package com.ssa.team3.backend.model.domain.IAM;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> getUserById(UUID userId);
    Optional<User> getUserByUsername(String username);

    void insertUser(String username, String hash, String firstName, String lastName);
}
