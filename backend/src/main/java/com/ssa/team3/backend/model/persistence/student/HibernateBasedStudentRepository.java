package com.ssa.team3.backend.model.persistence.student;

import com.ssa.team3.backend.model.domain.student.Student;
import com.ssa.team3.backend.model.domain.student.StudentRepository;
import com.ssa.team3.backend.model.persistence.HibernateUtil;
import com.ssa.team3.backend.model.persistence.IAM.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class HibernateBasedStudentRepository implements StudentRepository {
    @Inject HibernateUtil hibernate;

    @Override
    public Optional<Student> getStudentByTutorById(UUID tutorId, UUID id) {
        Session session = hibernate.beginTransaction();

        StudentEntity studentEntity = session.get(StudentEntity.class, id);
        if (studentEntity == null || !studentEntity.getTutor().getId().equals(tutorId)) {
            hibernate.endTransaction(session);
            return Optional.empty();
        }

        hibernate.endTransaction(session);
        return Optional.of(studentEntity).map(StudentEntity::toModel);
    }

    @Override
    public List<Student> getAllStudentsByTutor(UUID tutorId) {
        Session session = hibernate.beginTransaction();

        TypedQuery<StudentEntity> query = session.createQuery("select s from StudentEntity s where s.tutor.id = :tutorId", StudentEntity.class);
        query.setParameter("tutorId", tutorId);
        List<StudentEntity> students = query.getResultList();

        hibernate.endTransaction(session);

        return students.stream().map(StudentEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public Optional<Student> insertStudentByTutor(UUID tutorId, String firstName, String lastName, String group) {
        Session session = hibernate.beginTransaction();

        UserEntity tutor = session.get(UserEntity.class, tutorId);
        if (tutor == null){
            hibernate.endTransaction(session);
            return Optional.empty();
        }
        StudentEntity studentEntity = new StudentEntity(firstName, lastName, group, tutor);
        session.persist(studentEntity);

        hibernate.endTransaction(session);

        return Optional.of(studentEntity).map(StudentEntity::toModel);
    }

    @Override
    public boolean updateStudentByTutor(UUID tutorId, UUID id, String firstName, String lastName, String group) {
        Session session = hibernate.beginTransaction();

        StudentEntity studentEntity = session.get(StudentEntity.class, id);
        if (studentEntity == null || !studentEntity.getTutor().getId().equals(tutorId)){
            hibernate.endTransaction(session);
            return false;
        }

        session.evict(studentEntity);
        studentEntity.setFirstName(firstName);
        studentEntity.setLastName(lastName);
        studentEntity.setGroup(group);
        session.merge(studentEntity);

        hibernate.endTransaction(session);
        return true;
    }

    @Override
    public boolean deleteStudentByTutor(UUID tutorId, UUID id) {
        Session session = hibernate.beginTransaction();

        StudentEntity studentEntity = session.get(StudentEntity.class, id);
        if (studentEntity == null || !studentEntity.getTutor().getId().equals(tutorId)){
            hibernate.endTransaction(session);
            return false;
        }

        session.remove(studentEntity);
        hibernate.endTransaction(session);
        return true;
    }
}
