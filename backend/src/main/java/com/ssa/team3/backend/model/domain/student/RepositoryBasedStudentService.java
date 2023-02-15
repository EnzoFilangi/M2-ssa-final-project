package com.ssa.team3.backend.model.domain.student;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RepositoryBasedStudentService implements StudentService {
    @Inject StudentRepository studentRepository;
}
