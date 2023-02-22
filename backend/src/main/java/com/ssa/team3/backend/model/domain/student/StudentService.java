package com.ssa.team3.backend.model.domain.student;

import com.ssa.team3.backend.model.domain.internship.Internship;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface StudentService {
    List<Student> getStudents(UUID tutorId);

    Optional<Student> getStudent(UUID tutorId, UUID id);

    Optional<Student> addStudent(UUID tutorId, String firstName, String lastName, String group);

    boolean updateStudent(UUID tutorId, UUID id, String firstName, String lastName, String group);

    boolean deleteStudent(UUID tutorId, UUID id);

    Set<Internship> getInternshipsForStudent(UUID tutorId, UUID id);
}
