package com.ssa.team3.backend.controller.http.IAM;

import com.ssa.team3.backend.controller.http.IAM.annotations.Secured;
import com.ssa.team3.backend.controller.http.IAM.dto.request.LoginRequest;
import com.ssa.team3.backend.controller.http.IAM.dto.request.RegisterRequest;
import com.ssa.team3.backend.controller.http.IAM.dto.response.UserInfoResponse;
import com.ssa.team3.backend.model.domain.IAM.IAMService;
import com.ssa.team3.backend.model.domain.IAM.Session;
import com.ssa.team3.backend.model.domain.IAM.User;
import com.ssa.team3.backend.model.domain.IAM.exceptions.InvalidCredentialsException;
import com.ssa.team3.backend.model.domain.IAM.exceptions.UserAlreadyExistsException;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.Optional;
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
    public Response login(@Valid @NotNull LoginRequest body){
        try {
            Session session = iamService.login(body.getUsername(), body.getPassword());

            String sessionCookie = createSessionCookie(session.getId().toString());
            return Response.status(Response.Status.CREATED).header("Set-Cookie", sessionCookie).build();
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

    @GET
    @Path("/session")
    @Secured
    @Produces(APPLICATION_JSON)
    public UserInfoResponse loggedInUserInfo(@Context SecurityContext securityContext){
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        Optional<UserInfoResponse> responseDto = iamService.getUser(userId).map(this::toUserInfoResponse);
        if (responseDto.isEmpty()){
            throw new NotFoundException();
        }
        return responseDto.get();
    }

    @POST
    @Path("/user")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response register(@Valid @NotNull RegisterRequest body){
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
     * <li>sameSite : None
     * <li>maxAge : 24h
     * <li>secure : true
     * <li>httpOnly : true
     * </ul>
     */
    private String createSessionCookie(String sessionId){
        return "sessionId=" + sessionId + ";SameSite=None;Max-Age=86400;Secure;HttpOnly;Path=/";
    }

    private UserInfoResponse toUserInfoResponse(User user){
        return new UserInfoResponse(user);
    }
}
