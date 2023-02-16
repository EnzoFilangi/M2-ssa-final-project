package com.ssa.team3.backend.model.domain.student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentService {
    List<Student> getStudents();

    Optional<Student> getStudent(UUID id);

    Student addStudent(String firstName, String lastName, String group);

    void updateStudent(UUID id, String firstName, String lastName, String group);

    boolean deleteStudent(UUID id);
}
