package com.ssa.team3.backend.controller.http.student.dto.response;

import com.ssa.team3.backend.model.domain.student.Student;

import java.util.UUID;

public class StudentResponse {
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String group;

    public StudentResponse(Student student) {
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.group = student.getGroup();
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
}
