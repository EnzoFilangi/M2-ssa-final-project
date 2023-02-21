package com.ssa.team3.backend.model.domain.student;

import com.ssa.team3.backend.model.domain.IAM.User;
import com.ssa.team3.backend.model.domain.internship.Internship;

import java.util.Set;
import java.util.UUID;

public class Student {
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String group;
    private final Set<Internship> internships;
    private final User tutor;

    public Student(UUID id, String firstName, String lastName, String group, Set<Internship> internships, User tutor) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
        this.internships = internships;
        this.tutor = tutor;
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

    public User getTutor() {
        return tutor;
    }
}
