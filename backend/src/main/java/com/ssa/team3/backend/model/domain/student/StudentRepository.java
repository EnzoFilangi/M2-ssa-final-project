package com.ssa.team3.backend.model.domain.student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository {
    Optional<Student> getStudentById(UUID id);
    List<Student> getAllStudents();
    Student insertStudent(String firstName, String lastName, String group);
    boolean updateStudent(UUID id, String firstName, String lastName, String group);
    boolean deleteStudent(UUID id);
}
