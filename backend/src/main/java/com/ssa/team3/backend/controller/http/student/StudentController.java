package com.ssa.team3.backend.controller.http.student;

import com.ssa.team3.backend.controller.http.student.dto.request.StudentRequest;
import com.ssa.team3.backend.controller.http.student.dto.response.StudentResponse;
import com.ssa.team3.backend.model.domain.student.Student;
import com.ssa.team3.backend.model.domain.student.StudentService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/students")
@RequestScoped
public class StudentController {
    @Inject StudentService studentService;

    @GET
    @Produces(APPLICATION_JSON)
    public List<StudentResponse> getAllStudents() {
        return studentService.getStudents().stream().map(this::toStudentResponse).collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public StudentResponse getStudent(@PathParam("id") UUID id) {
        Optional<StudentResponse> responseDto = studentService.getStudent(id).map(this::toStudentResponse);
        if (responseDto.isEmpty()){
            throw new NotFoundException();
        }
        return responseDto.get();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response createStudent(@Valid StudentRequest body) {
        Student newStudent = studentService.addStudent(body.getFirstName(), body.getLastName(), body.getGroup());
        return Response.status(Response.Status.CREATED).entity(toStudentResponse(newStudent)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(APPLICATION_JSON)
    public void updateStudent(@PathParam("id") UUID id, @Valid StudentRequest body){
        if (!studentService.updateStudent(id, body.getFirstName(), body.getLastName(), body.getGroup())){
            throw new NotFoundException();
        }
    }

    @DELETE
    @Path("/{id}")
    public void deleteStudent(@PathParam("id") UUID id) {
        if (!studentService.deleteStudent(id)) {
            throw new NotFoundException();
        }
    }


    private StudentResponse toStudentResponse(Student student) {
        return new StudentResponse(student);
    }
}