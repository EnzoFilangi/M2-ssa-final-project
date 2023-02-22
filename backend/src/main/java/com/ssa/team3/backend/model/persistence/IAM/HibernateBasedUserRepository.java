package com.ssa.team3.backend.model.persistence.IAM;

import com.ssa.team3.backend.model.domain.IAM.User;
import com.ssa.team3.backend.model.domain.IAM.UserRepository;
import com.ssa.team3.backend.model.persistence.HibernateUtil;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class HibernateBasedUserRepository implements UserRepository {
    @Override
    public Optional<User> getUserById(UUID userId) {
        Session session = HibernateUtil.beginTransaction();

        UserEntity userEntity = session.get(UserEntity.class, userId);

        HibernateUtil.endTransaction(session);

        return Optional.ofNullable(userEntity).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        Session session = HibernateUtil.beginTransaction();

        Query<UserEntity> query = session.createQuery("select user from UserEntity user where user.username = :username", UserEntity.class);
        query.setParameter("username", username);
        UserEntity userEntity = query.getSingleResultOrNull();

        HibernateUtil.endTransaction(session);

        return Optional.ofNullable(userEntity).map(UserEntity::toModel);
    }

    @Override
    public void insertUser(String username, String hash, String firstName, String lastName) {
        Session session = HibernateUtil.beginTransaction();

        UserEntity userEntity = new UserEntity(username, hash, firstName, lastName);
        session.persist(userEntity);

        HibernateUtil.endTransaction(session);
    }
}
