package com.ssa.team3.backend.model.persistence.IAM;

import com.ssa.team3.backend.model.domain.IAM.Session;
import com.ssa.team3.backend.model.domain.IAM.SessionRepository;
import com.ssa.team3.backend.model.persistence.HibernateUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@ApplicationScoped
public class HibernateBasedSessionRepository implements SessionRepository {
    @Inject HibernateUtil hibernate;

    @Override
    public Session insertSession(UUID userId) {
        org.hibernate.Session hibernateSession = hibernate.beginTransaction();

        SessionEntity sessionEntity = new SessionEntity(userId);
        hibernateSession.persist(sessionEntity);

        hibernate.endTransaction(hibernateSession);

        return sessionEntity.toModel();
    }
}
