package com.ssa.team3.backend.model.domain.IAM;

import com.ssa.team3.backend.model.domain.student.Student;

import java.util.Set;
import java.util.UUID;

public class User {
    private final UUID id;

    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final Set<Student> students;

    public User(UUID id, String username, String password, String firstName, String lastName, Set<Student> students) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.students = students;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Set<Student> getStudents() {
        return students;
    }
}
