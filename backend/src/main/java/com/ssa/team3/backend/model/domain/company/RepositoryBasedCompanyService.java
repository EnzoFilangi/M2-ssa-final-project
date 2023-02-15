package com.ssa.team3.backend.model.domain.company;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RepositoryBasedCompanyService implements CompanyService {
    @Inject CompanyRepository companyRepository;
}
