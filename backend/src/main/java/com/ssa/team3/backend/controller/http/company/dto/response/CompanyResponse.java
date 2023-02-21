package com.ssa.team3.backend.controller.http.company.dto.response;

import com.ssa.team3.backend.model.domain.company.Company;

import java.util.UUID;

public class CompanyResponse {
    private final UUID id;
    private final String name;
    private final String address;

    public CompanyResponse(Company company){
        this.id = company.getId();
        this.name = company.getName();
        this.address = company.getAddress();
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
}
