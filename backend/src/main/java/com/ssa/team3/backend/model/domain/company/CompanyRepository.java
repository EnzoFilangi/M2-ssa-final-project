package com.ssa.team3.backend.model.domain.company;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    Optional<Company> getCompanyRelatedToTutor(UUID tutorId, UUID id);

    Optional<Company> insertCompany(String name, String address);

    boolean updateCompany(UUID tutorId, UUID id, String name, String address);
}
