package com.ssa.team3.backend.model.persistence.IAM;

import com.ssa.team3.backend.model.domain.IAM.Session;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "SESSIONS")
public class SessionEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    // No relation on purpose as we want to store a plain value and not a reference to a user
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    public SessionEntity() {
    }

    public SessionEntity(UUID userId) {
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Session toModel(){
        return new Session(id, userId);
    }
}
