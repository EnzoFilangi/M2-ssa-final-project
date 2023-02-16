package com.ssa.team3.backend.model.domain.IAM;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Session {
    private final UUID id;
    private final UUID userId;

    public Session(UUID id, UUID userId) {
        this.id = id;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public List<String> getRoles() {
        return new ArrayList<>(){{
            this.add("User");
        }};
    }
}
