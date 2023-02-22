package com.ssa.team3.backend.controller.http.student;

import com.ssa.team3.backend.controller.http.IAM.annotations.Secured;
import com.ssa.team3.backend.controller.http.internship.dto.response.InternshipResponse;
import com.ssa.team3.backend.controller.http.student.dto.request.StudentRequest;
import com.ssa.team3.backend.controller.http.student.dto.response.StudentResponse;
import com.ssa.team3.backend.model.domain.internship.Internship;
import com.ssa.team3.backend.model.domain.student.Student;
import com.ssa.team3.backend.model.domain.student.StudentService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/students")
@RequestScoped
public class StudentController {
    @Inject StudentService studentService;

    @GET
    @Secured
    @Produces(APPLICATION_JSON)
    public List<StudentResponse> getAllStudents(@Context SecurityContext securityContext) {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        return studentService.getStudents(userId).stream().map(this::toStudentResponse).collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Secured
    @Produces(APPLICATION_JSON)
    public StudentResponse getStudent(@PathParam("id") UUID id, @Context SecurityContext securityContext) {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        Optional<StudentResponse> responseDto = studentService.getStudent(userId, id).map(this::toStudentResponse);
        if (responseDto.isEmpty()){
            throw new NotFoundException();
        }
        return responseDto.get();
    }

    @GET
    @Path("/{id}/internships")
    @Secured
    @Produces(APPLICATION_JSON)
    public Set<InternshipResponse> getInternshipsForStudent(@PathParam("id") UUID id, @Context SecurityContext securityContext) {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());

        Optional<Student> studentOptional = studentService.getStudent(userId, id);
        if (studentOptional.isEmpty()){
            throw new NotFoundException();
        }

        return studentOptional.get().getInternships().stream().map(this::toInternshipResponse).collect(Collectors.toSet());
    }

    @POST
    @Secured
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response createStudent(@Valid @NotNull StudentRequest body, @Context SecurityContext securityContext) {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        Optional<Student> newStudent = studentService.addStudent(userId, body.getFirstName(), body.getLastName(), body.getGroup());

        if (newStudent.isEmpty()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.status(Response.Status.CREATED).entity(toStudentResponse(newStudent.get())).build();
    }

    @PUT
    @Path("/{id}")
    @Secured
    @Consumes(APPLICATION_JSON)
    public void updateStudent(@PathParam("id") UUID id, @Valid @NotNull StudentRequest body, @Context SecurityContext securityContext){
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        if (!studentService.updateStudent(userId, id, body.getFirstName(), body.getLastName(), body.getGroup())){
            throw new NotFoundException();
        }
    }

    @DELETE
    @Path("/{id}")
    @Secured
    public void deleteStudent(@PathParam("id") UUID id, @Context SecurityContext securityContext) {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        if (!studentService.deleteStudent(userId, id)) {
            throw new NotFoundException();
        }
    }


    private StudentResponse toStudentResponse(Student student) {
        return new StudentResponse(student);
    }
    private InternshipResponse toInternshipResponse(Internship internship) {
        return new InternshipResponse(internship);
    }
}