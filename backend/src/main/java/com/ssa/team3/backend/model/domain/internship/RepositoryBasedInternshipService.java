package com.ssa.team3.backend.model.domain.internship;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RepositoryBasedInternshipService implements InternshipService {
    @Inject InternshipRepository internshipRepository;
}
