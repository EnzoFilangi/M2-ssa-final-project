package com.ssa.team3.backend.controller.http.company;

import com.ssa.team3.backend.controller.http.IAM.annotations.Secured;
import com.ssa.team3.backend.controller.http.company.dto.request.CompanyRequest;
import com.ssa.team3.backend.controller.http.company.dto.response.CompanyResponse;
import com.ssa.team3.backend.model.domain.company.Company;
import com.ssa.team3.backend.model.domain.company.CompanyService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.Optional;
import java.util.UUID;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/companies")
@RequestScoped
public class CompanyController {
    @Inject CompanyService companyService;

    @GET
    @Path("/{id}")
    @Secured
    @Produces(APPLICATION_JSON)
    public CompanyResponse getCompany(@PathParam("id") UUID id, @Context SecurityContext securityContext) {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        Optional<CompanyResponse> responseDto = companyService.getCompany(userId, id).map(this::toCompanyResponse);

        if (responseDto.isEmpty()){
            throw new NotFoundException();
        }
        return responseDto.get();
    }

    @POST
    @Secured
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response createCompany(@Valid @NotNull CompanyRequest body) {
        Optional<Company> newCompany = companyService.addCompany(body.getName(), body.getAddress());

        if (newCompany.isEmpty()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.status(Response.Status.CREATED).entity(toCompanyResponse(newCompany.get())).build();
    }

    @PUT
    @Path("/{id}")
    @Secured
    @Consumes(APPLICATION_JSON)
    public void updateCompany(@PathParam("id") UUID id, @Valid @NotNull CompanyRequest body, @Context SecurityContext securityContext){
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());

        if (!companyService.updateCompany(userId, id, body.getName(), body.getAddress())) {
            throw new NotFoundException();
        }
    }

    private CompanyResponse toCompanyResponse(Company company){
        return new CompanyResponse(company);
    }
}
