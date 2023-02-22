package com.ssa.team3.backend.model.domain.student;

import com.ssa.team3.backend.model.domain.internship.Internship;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.*;

@ApplicationScoped
public class RepositoryBasedStudentService implements StudentService {
    @Inject StudentRepository studentRepository;

    @Override
    public List<Student> getStudents(UUID tutorId) {
        return studentRepository.getAllStudentsByTutor(tutorId);
    }

    @Override
    public Optional<Student> getStudent(UUID tutorId, UUID id) {
        return studentRepository.getStudentByTutorById(tutorId, id);
    }

    @Override
    public Optional<Student> addStudent(UUID tutorId, String firstName, String lastName, String group) {
        return studentRepository.insertStudentByTutor(tutorId, firstName, lastName, group);
    }

    @Override
    public boolean updateStudent(UUID tutorId, UUID id, String firstName, String lastName, String group) {
        return studentRepository.updateStudentByTutor(tutorId, id, firstName, lastName, group);
    }

    @Override
    public boolean deleteStudent(UUID tutorId, UUID id) {
        return studentRepository.deleteStudentByTutor(tutorId, id);
    }

    @Override
    public Set<Internship> getInternshipsForStudent(UUID tutorId, UUID id) {
        Optional<Student> studentOptional = studentRepository.getStudentByTutorByIdWithRelations(tutorId, id);
        return studentOptional.map(Student::getInternships).orElse(new HashSet<>());
    }
}
