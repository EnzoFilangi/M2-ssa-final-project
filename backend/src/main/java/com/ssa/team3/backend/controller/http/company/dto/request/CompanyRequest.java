package com.ssa.team3.backend.controller.http.company.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CompanyRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String address;

    public CompanyRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
