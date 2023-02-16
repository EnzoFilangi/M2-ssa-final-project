package com.ssa.team3.backend.model.domain.IAM;

import java.util.UUID;

public class User {
    private final UUID id;

    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;

    public User(UUID id, String username, String password, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
