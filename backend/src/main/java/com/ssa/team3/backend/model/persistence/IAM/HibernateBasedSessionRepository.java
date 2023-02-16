package com.ssa.team3.backend.model.persistence.IAM;

import com.ssa.team3.backend.model.domain.IAM.Session;
import com.ssa.team3.backend.model.domain.IAM.SessionRepository;
import com.ssa.team3.backend.model.persistence.HibernateUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class HibernateBasedSessionRepository implements SessionRepository {
    @Inject HibernateUtil hibernate;

    @Override
    public Optional<Session> getSession(UUID sessionId) {
        org.hibernate.Session hibernateSession = hibernate.beginTransaction();

        SessionEntity sessionEntity = hibernateSession.get(SessionEntity.class, sessionId);

        hibernate.endTransaction(hibernateSession);

        return Optional.ofNullable(sessionEntity).map(SessionEntity::toModel);
    }

    @Override
    public Session insertSession(UUID userId) {
        org.hibernate.Session hibernateSession = hibernate.beginTransaction();

        SessionEntity sessionEntity = new SessionEntity(userId);
        hibernateSession.persist(sessionEntity);

        hibernate.endTransaction(hibernateSession);

        return sessionEntity.toModel();
    }

    @Override
    public void deleteSession(UUID sessionId) {
        org.hibernate.Session hibernateSession = hibernate.beginTransaction();

        SessionEntity sessionEntity = hibernateSession.get(SessionEntity.class, sessionId);
        hibernateSession.remove(sessionEntity);

        hibernate.endTransaction(hibernateSession);
    }
}
