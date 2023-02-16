package com.ssa.team3.backend.model.domain.IAM;

import java.util.UUID;

public interface SessionRepository {
    Session insertSession(UUID userId);
}
