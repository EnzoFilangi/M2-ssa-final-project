package com.ssa.team3.backend.model.domain.IAM;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository {
    Optional<Session> getSession(UUID sessionId);
    Session insertSession(UUID userId);
    void deleteSession(UUID sessionId);
    void setSessionExpiryDate(UUID sessionId, Date newExpiryDate);
}
