package com.ssa.team3.backend.model.domain.company;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class RepositoryBasedCompanyService implements CompanyService {
    @Inject CompanyRepository companyRepository;

    @Override
    public Optional<Company> getCompany(UUID tutorId, UUID id) {
        return companyRepository.getCompanyRelatedToTutor(tutorId, id);
    }

    @Override
    public Optional<Company> addCompany(String name, String address) {
        return companyRepository.insertCompany(name, address);
    }

    @Override
    public boolean updateCompany(UUID tutorId, UUID id, String name, String address) {
        return companyRepository.updateCompany(tutorId, id, name, address);
    }
}
