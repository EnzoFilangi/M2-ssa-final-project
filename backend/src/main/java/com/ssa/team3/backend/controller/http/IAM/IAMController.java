package com.ssa.team3.backend.controller.http.IAM;

import com.ssa.team3.backend.controller.http.IAM.annotations.Secured;
import com.ssa.team3.backend.controller.http.IAM.dto.request.LoginRequest;
import com.ssa.team3.backend.controller.http.IAM.dto.request.RegisterRequest;
import com.ssa.team3.backend.model.domain.IAM.IAMService;
import com.ssa.team3.backend.model.domain.IAM.Session;
import com.ssa.team3.backend.model.domain.IAM.exceptions.InvalidCredentialsException;
import com.ssa.team3.backend.model.domain.IAM.exceptions.UserAlreadyExistsException;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.UUID;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

// Most of the structure for the authentication implementation comes from : https://stackoverflow.com/a/26778123
@Path("/authentication")
public class IAMController {
    @Inject
    IAMService iamService;

    @POST
    @Path("/session")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response login(@Valid LoginRequest body){
        try {
            Session session = iamService.login(body.getUsername(), body.getPassword());

            NewCookie sessionCookie = createSessionCookie(session.getId().toString());
            return Response.status(Response.Status.CREATED).cookie(sessionCookie).build();
        } catch (InvalidCredentialsException e){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @DELETE
    @Path("/session")
    @Secured
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public void logout(@CookieParam("sessionId") Cookie cookie){
        if (cookie != null){
            iamService.logout(UUID.fromString(cookie.getValue()));
        }
    }

    @POST
    @Path("/user")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response register(@Valid RegisterRequest body){
        try {
            iamService.register(body.getUsername(), body.getPassword(), body.getFirstName(), body.getLastName());

            return Response.status(Response.Status.CREATED).build();
        } catch (UserAlreadyExistsException | InvalidCredentialsException e){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    /**
     * Creates a cookie named "sessionId" storing the sessionId with the following parameters :
     * <ul>
     * <li>path : "/"
     * <li>domain : ""
     * <li>maxAge : 24h
     * <li>secure : true
     * <li>httpOnly : true
     * </ul>
     */
    private NewCookie createSessionCookie(String sessionId){
        return new NewCookie("sessionId", sessionId, "/", "", "", 60*60*24, true, true);
    }
}
