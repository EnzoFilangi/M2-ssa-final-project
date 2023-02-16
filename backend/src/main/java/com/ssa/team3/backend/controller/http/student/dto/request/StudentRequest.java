package com.ssa.team3.backend.controller.http.student.dto.request;

import jakarta.validation.constraints.NotBlank;

public class StudentRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String group;

    public StudentRequest(){}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGroup() {
        return group;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
