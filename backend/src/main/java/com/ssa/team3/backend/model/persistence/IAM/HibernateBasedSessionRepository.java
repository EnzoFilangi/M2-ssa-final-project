package com.ssa.team3.backend.model.persistence.IAM;

import com.ssa.team3.backend.model.domain.IAM.Session;
import com.ssa.team3.backend.model.domain.IAM.SessionRepository;
import com.ssa.team3.backend.model.persistence.HibernateUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Calendar;
import java.util.Date;
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
        // From : https://stackoverflow.com/a/1005550
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 1);
        Date sessionExpiryDate = c.getTime();

        org.hibernate.Session hibernateSession = hibernate.beginTransaction();

        SessionEntity sessionEntity = new SessionEntity(userId, sessionExpiryDate);
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

    @Override
    public void setSessionExpiryDate(UUID sessionId, Date newExpiryDate) {
        org.hibernate.Session hibernateSession = hibernate.beginTransaction();

        SessionEntity sessionEntity = hibernateSession.get(SessionEntity.class, sessionId);
        if (sessionEntity == null){
            hibernate.endTransaction(hibernateSession);
            return;
        }

        hibernateSession.evict(sessionEntity);
        sessionEntity.setExpiryDate(newExpiryDate);
        hibernateSession.merge(sessionEntity);

        hibernate.endTransaction(hibernateSession);
    }
}
