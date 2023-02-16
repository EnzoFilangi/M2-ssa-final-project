package com.ssa.team3.backend.model.persistence.IAM;

import com.ssa.team3.backend.model.domain.IAM.User;
import com.ssa.team3.backend.model.domain.IAM.UserRepository;
import com.ssa.team3.backend.model.persistence.HibernateUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.Session;

import java.util.Optional;

@ApplicationScoped
public class HibernateBasedUserRepository implements UserRepository {
    @Inject HibernateUtil hibernate;

    @Override
    public Optional<User> getUserByUsername(String username) {
        Session session = hibernate.beginTransaction();

        UserEntity userEntity = session.createQuery("select user from UserEntity user where user.username = username", UserEntity.class).getSingleResultOrNull();

        hibernate.endTransaction(session);

        return Optional.ofNullable(userEntity).map(UserEntity::toModel);
    }

    @Override
    public void insertUser(String username, String hash) {
        Session session = hibernate.beginTransaction();

        UserEntity userEntity = new UserEntity(username, hash);
        session.persist(userEntity);

        hibernate.endTransaction(session);
    }
}
