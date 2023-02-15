package com.ssa.team3.backend.model.models;

import java.util.Set;
import java.util.UUID;

public class Student {
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String group;
    private final Set<Internship> internships;

    public Student(UUID id, String firstName, String lastName, String group, Set<Internship> internships) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
        this.internships = internships;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGroup() {
        return group;
    }

    public Set<Internship> getInternships() {
        return internships;
    }
}
