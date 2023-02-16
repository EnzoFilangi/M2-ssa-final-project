package com.ssa.team3.backend.model.domain.student;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class RepositoryBasedStudentService implements StudentService {
    @Inject StudentRepository studentRepository;

    @Override
    public List<Student> getStudents() {
        return studentRepository.getAllStudents();
    }

    @Override
    public Optional<Student> getStudent(UUID id) {
        return studentRepository.getStudentById(id);
    }

    @Override
    public Student addStudent(String firstName, String lastName, String group) {
        return studentRepository.insertStudent(firstName, lastName, group);
    }

    @Override
    public void updateStudent(UUID id, String firstName, String lastName, String group) {
        studentRepository.updateStudent(id, firstName, lastName, group);
    }

    @Override
    public boolean deleteStudent(UUID id) {
        return studentRepository.deleteStudent(id);
    }
}
