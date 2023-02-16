package com.ssa.team3.backend.model.domain.company;

import com.ssa.team3.backend.model.domain.internship.Internship;

import java.util.UUID;

public class Company {
    private final UUID id;
    private final String name;
    private final String address;
    private final Internship internship;

    public Company(UUID id, String name, String address, Internship internship) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.internship = internship;
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

    public Internship getInternship() {
        return internship;
    }
}
