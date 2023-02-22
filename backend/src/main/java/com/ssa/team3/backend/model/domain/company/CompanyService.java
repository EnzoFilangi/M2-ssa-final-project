package com.ssa.team3.backend.model.domain.company;

import java.util.Optional;
import java.util.UUID;

public interface CompanyService {
    Optional<Company> getCompany(UUID tutorId, UUID id);

    Optional<Company> addCompany(String name, String address);

    boolean updateCompany(UUID tutorId, UUID id, String name, String address);
}
