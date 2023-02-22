package com.ssa.team3.backend.controller.http.internship;

import com.ssa.team3.backend.controller.http.IAM.annotations.Secured;
import com.ssa.team3.backend.controller.http.internship.dto.request.CreateInternshipRequest;
import com.ssa.team3.backend.controller.http.internship.dto.request.EditInternshipRequest;
import com.ssa.team3.backend.controller.http.internship.dto.response.InternshipResponse;
import com.ssa.team3.backend.model.domain.internship.Internship;
import com.ssa.team3.backend.model.domain.internship.InternshipService;
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

@Path("/internships")
@RequestScoped
public class InternshipController {
    @Inject InternshipService internshipService;

    @GET
    @Path("/{id}")
    @Secured
    @Produces(APPLICATION_JSON)
    public InternshipResponse getInternship(@PathParam("id") UUID id, @Context SecurityContext securityContext) {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        Optional<InternshipResponse> responseDto = internshipService.getInternship(userId, id).map(this::toInternshipResponse);
        if (responseDto.isEmpty()){
            throw new NotFoundException();
        }
        return responseDto.get();
    }

    @POST
    @Secured
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response createInternship(@Valid @NotNull CreateInternshipRequest body, @Context SecurityContext securityContext) {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        Optional<Internship> newInternship = internshipService.addInternship(
            userId,
            body.getStudentId(),
            body.getCompanyId(),
            body.getStartDate(),
            body.getEndDate(),
            body.getCahierDesCharges(),
            body.getFicheVisite(),
            body.getFicheEvaluationEntreprise(),
            body.getSondageWeb(),
            body.getRapportRendu(),
            body.getSoutenance(),
            body.getVisitePlanifiee(),
            body.getVisiteFaite(),
            body.getNoteTech(),
            body.getNoteCom()
        );

        if (newInternship.isEmpty()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.status(Response.Status.CREATED).entity(toInternshipResponse(newInternship.get())).build();
    }

    @PUT
    @Path("/{id}")
    @Secured
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public void updateInternship(@PathParam("id") UUID id, @Valid @NotNull EditInternshipRequest body, @Context SecurityContext securityContext) {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        if(!internshipService.updateInternship(
            userId,
            id,
            body.getStartDate(),
            body.getEndDate(),
            body.getCahierDesCharges(),
            body.getFicheVisite(),
            body.getFicheEvaluationEntreprise(),
            body.getSondageWeb(),
            body.getRapportRendu(),
            body.getSoutenance(),
            body.getVisitePlanifiee(),
            body.getVisiteFaite(),
            body.getNoteTech(),
            body.getNoteCom()
        )){
            throw new NotFoundException();
        }
    }

    @DELETE
    @Path("/{id}")
    @Secured
    public void deleteInternship(@PathParam("id") UUID id, @Context SecurityContext securityContext) {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        if (!internshipService.deleteInternship(userId, id)) {
            throw new NotFoundException();
        }
    }

    private InternshipResponse toInternshipResponse(Internship internship){
        return new InternshipResponse(internship);
    }
}
