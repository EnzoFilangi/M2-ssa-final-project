package com.ssa.team3.backend.controller.http.student;

import com.ssa.team3.backend.controller.http.student.dto.response.StudentResponse;
import com.ssa.team3.backend.model.domain.student.Student;
import com.ssa.team3.backend.model.domain.student.StudentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/students")
public class StudentController {
    @Inject StudentService studentService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<StudentResponse> getAllStudents() {
        return studentService.getStudents().stream().map(this::toStudentResponse).collect(Collectors.toList());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getStudent(@PathParam("id") UUID id) {
        Optional<StudentResponse> responseDto = studentService.getStudent(id).map(this::toStudentResponse);
        if (responseDto.isPresent()){
            return Response.ok(responseDto.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private StudentResponse toStudentResponse(Student student) {
        return new StudentResponse(student);
    }
}