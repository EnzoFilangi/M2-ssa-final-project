package com.ssa.team3.backend.model.persistence.student;

import com.ssa.team3.backend.model.domain.student.Student;
import com.ssa.team3.backend.model.domain.student.StudentRepository;
import com.ssa.team3.backend.model.persistence.HibernateUtil;
import com.ssa.team3.backend.model.persistence.IAM.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.FetchType;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class HibernateBasedStudentRepository implements StudentRepository {

    private Optional<Student> getStudentByTutorById(UUID tutorId, UUID id, FetchType fetchType){
        Session session = HibernateUtil.beginTransaction();

        StudentEntity studentEntity = session.get(StudentEntity.class, id);
        if (studentEntity == null || !studentEntity.getTutor().getId().equals(tutorId)) {
            HibernateUtil.endTransaction(session);
            return Optional.empty();
        }

        Optional<Student> toReturn = Optional.of(studentEntity).map(entity -> entity.toModel(fetchType));
        HibernateUtil.endTransaction(session);
        return toReturn;
    }

    @Override
    public Optional<Student> getStudentByTutorById(UUID tutorId, UUID id) {
        return getStudentByTutorById(tutorId, id, FetchType.LAZY);
    }

    @Override
    public Optional<Student> getStudentByTutorByIdWithRelations(UUID tutorId, UUID id) {
        return getStudentByTutorById(tutorId, id, FetchType.EAGER);
    }

    @Override
    public List<Student> getAllStudentsByTutor(UUID tutorId) {
        Session session = HibernateUtil.beginTransaction();

        TypedQuery<StudentEntity> query = session.createQuery("select s from StudentEntity s where s.tutor.id = :tutorId", StudentEntity.class);
        query.setParameter("tutorId", tutorId);
        List<StudentEntity> students = query.getResultList();

        HibernateUtil.endTransaction(session);

        return students.stream().map(StudentEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public Optional<Student> insertStudentByTutor(UUID tutorId, String firstName, String lastName, String group) {
        Session session = HibernateUtil.beginTransaction();

        UserEntity tutor = session.get(UserEntity.class, tutorId);
        if (tutor == null){
            HibernateUtil.endTransaction(session);
            return Optional.empty();
        }
        StudentEntity studentEntity = new StudentEntity(firstName, lastName, group, tutor);
        session.persist(studentEntity);

        HibernateUtil.endTransaction(session);

        return Optional.of(studentEntity).map(StudentEntity::toModel);
    }

    @Override
    public boolean updateStudentByTutor(UUID tutorId, UUID id, String firstName, String lastName, String group) {
        Session session = HibernateUtil.beginTransaction();

        StudentEntity studentEntity = session.get(StudentEntity.class, id);
        if (studentEntity == null || !studentEntity.getTutor().getId().equals(tutorId)){
            HibernateUtil.endTransaction(session);
            return false;
        }

        session.evict(studentEntity);
        studentEntity.setFirstName(firstName);
        studentEntity.setLastName(lastName);
        studentEntity.setGroup(group);
        session.merge(studentEntity);

        HibernateUtil.endTransaction(session);
        return true;
    }

    @Override
    public boolean deleteStudentByTutor(UUID tutorId, UUID id) {
        Session session = HibernateUtil.beginTransaction();

        StudentEntity studentEntity = session.get(StudentEntity.class, id);
        if (studentEntity == null || !studentEntity.getTutor().getId().equals(tutorId)){
            HibernateUtil.endTransaction(session);
            return false;
        }

        session.remove(studentEntity);
        HibernateUtil.endTransaction(session);
        return true;
    }
}
