package com.ssa.team3.backend.model.domain.IAM;

import java.util.Optional;

public interface UserRepository {
    Optional<User> getUserByUsername(String username);

    void insertUser(String username, String hash);
}
