package com.ssa.team3.backend.controller.http.IAM.dto.response;

import com.ssa.team3.backend.model.domain.IAM.User;

import java.util.UUID;

public class UserInfoResponse {
    private final UUID id;
    private final String firstName;
    private final String lastName;

    public UserInfoResponse(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
