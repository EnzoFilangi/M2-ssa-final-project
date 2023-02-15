package com.ssa.team3.backend.model.domain.company;

import com.ssa.team3.backend.model.domain.internship.Internship;

import java.util.Set;
import java.util.UUID;

public class Company {
    private final UUID id;
    private final String name;
    private final String address;
    private final Set<Internship> internships;

    public Company(UUID id, String name, String address, Set<Internship> internships) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.internships = internships;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Set<Internship> getInternships() {
        return internships;
    }
}
