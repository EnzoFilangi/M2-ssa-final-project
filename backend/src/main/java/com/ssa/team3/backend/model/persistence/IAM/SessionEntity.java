package com.ssa.team3.backend.model.persistence.IAM;

import com.ssa.team3.backend.model.domain.IAM.Session;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    public SessionEntity() {
    }

    public SessionEntity(UUID userId, Date expiryDate) {
        this.userId = userId;
        this.expiryDate = expiryDate;
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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Session toModel(){
        return new Session(id, userId, expiryDate);
    }
}
