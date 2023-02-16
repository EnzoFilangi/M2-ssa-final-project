package com.ssa.team3.backend.model.persistence.student;

import com.ssa.team3.backend.model.domain.student.Student;
import com.ssa.team3.backend.model.domain.student.StudentRepository;
import com.ssa.team3.backend.model.persistence.HibernateUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class HibernateBasedStudentRepository implements StudentRepository {
    @Inject HibernateUtil hibernate;

    @Override
    public Optional<Student> getStudentById(UUID id) {
        Session session = hibernate.beginTransaction();

        StudentEntity studentEntity = session.get(StudentEntity.class, id);

        hibernate.endTransaction(session);

        return Optional.ofNullable(studentEntity).map(StudentEntity::toModel);
    }

    @Override
    public List<Student> getAllStudents() {
        Session session = hibernate.beginTransaction();

        List<StudentEntity> students = session.createQuery("select s from StudentEntity s", StudentEntity.class).getResultList();

        hibernate.endTransaction(session);

        return students.stream().map(StudentEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public Student insertStudent(String firstName, String lastName, String group) {
        Session session = hibernate.beginTransaction();

        StudentEntity studentEntity = new StudentEntity(firstName, lastName, group);
        session.persist(studentEntity);

        hibernate.endTransaction(session);

        return studentEntity.toModel();
    }

    @Override
    public boolean updateStudent(UUID id, String firstName, String lastName, String group) {
        Session session = hibernate.beginTransaction();

        StudentEntity studentEntity = session.get(StudentEntity.class, id);
        if (studentEntity == null){
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
    public boolean deleteStudent(UUID id) {
        Session session = hibernate.beginTransaction();

        StudentEntity studentEntity = session.get(StudentEntity.class, id);
        if (studentEntity == null){
            hibernate.endTransaction(session);
            return false;
        }

        session.remove(studentEntity);
        hibernate.endTransaction(session);
        return true;
    }
}
