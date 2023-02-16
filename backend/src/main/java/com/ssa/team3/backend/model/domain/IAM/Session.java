package com.ssa.team3.backend.model.domain.IAM;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Session {
    private final UUID id;
    private final UUID userId;
    private final Date expiryDate;

    public Session(UUID id, UUID userId, Date expiryDate) {
        this.id = id;
        this.userId = userId;
        this.expiryDate = expiryDate;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public List<String> getRoles() {
        return new ArrayList<>(){{
            this.add("User");
        }};
    }
}
