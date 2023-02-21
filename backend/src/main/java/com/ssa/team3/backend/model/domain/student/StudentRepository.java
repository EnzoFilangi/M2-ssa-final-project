package com.ssa.team3.backend.model.domain.student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository {
    Optional<Student> getStudentByTutorById(UUID tutorId, UUID id);
    List<Student> getAllStudentsByTutor(UUID tutorId);
    Optional<Student> insertStudentByTutor(UUID tutorId, String firstName, String lastName, String group);
    boolean updateStudentByTutor(UUID tutorId, UUID id, String firstName, String lastName, String group);
    boolean deleteStudentByTutor(UUID tutorId, UUID id);
}
