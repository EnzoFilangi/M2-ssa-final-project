package com.ssa.team3.backend.controller.http.student;

import com.ssa.team3.backend.controller.http.student.dto.request.StudentRequest;
import com.ssa.team3.backend.controller.http.student.dto.response.StudentResponse;
import com.ssa.team3.backend.model.domain.student.Student;
import com.ssa.team3.backend.model.domain.student.StudentService;
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
    public Response getStudent(@PathParam("id") UUID id) {
        Optional<StudentResponse> responseDto = studentService.getStudent(id).map(this::toStudentResponse);
        if (responseDto.isPresent()){
            return Response.ok(responseDto.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public StudentResponse createStudent(@Valid StudentRequest body){
        return toStudentResponse(studentService.addStudent(body.getFirstName(), body.getLastName(), body.getGroup()));
    }

    @PUT
    @Path("/{id}")
    @Consumes(APPLICATION_JSON)
    public Response updateStudent(@PathParam("id") UUID id, @Valid StudentRequest body){
        if (studentService.updateStudent(id, body.getFirstName(), body.getLastName(), body.getGroup())){
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    private StudentResponse toStudentResponse(Student student) {
        return new StudentResponse(student);
    }
}