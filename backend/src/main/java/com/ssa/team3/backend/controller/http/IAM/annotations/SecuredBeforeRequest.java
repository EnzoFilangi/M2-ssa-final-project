package com.ssa.team3.backend.controller.http.IAM.annotations;

import com.ssa.team3.backend.model.domain.IAM.IAMService;
import com.ssa.team3.backend.model.domain.IAM.Session;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * This class implements the part of the @Secured annotation that happens before the request.
 * It checks the session cookie sent with each request, and aborts the request if it is invalid.
 * It also handles the session lifecycle in case it has expired.
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecuredBeforeRequest implements ContainerRequestFilter {
    @Inject IAMService iamService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        try {
            Session currentSession = getSession(requestContext);
            tryInjectSession(requestContext, currentSession);
        } catch(InvalidSessionException e) {
            abortWithUnauthorized(requestContext);
        }
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }

    private Session getSession(ContainerRequestContext requestContext) throws InvalidSessionException {
        Cookie sessionCookie = getSessionCookie(requestContext);
        Optional<Session> sessionOptional = iamService.getSession(UUID.fromString(sessionCookie.getValue()));
        if (sessionOptional.isEmpty()){
            throw new InvalidSessionException();
        }
        return sessionOptional.get();
    }

    private Cookie getSessionCookie(ContainerRequestContext requestContext) throws InvalidSessionException {
        Cookie sessionCookie = requestContext.getCookies().get("sessionId");
        if (sessionCookie == null) {
            throw new InvalidSessionException();
        }
        return sessionCookie;
    }

    private void tryInjectSession(ContainerRequestContext requestContext, Session currentSession) throws InvalidSessionException {
        // If the session is valid, inject it in the current request. Otherwise delete it from the DB and reject the request.
        if (isSessionValid(currentSession)){
            injectSession(requestContext, currentSession);
        } else {
            iamService.removeSession(currentSession.getId());
            throw new InvalidSessionException();
        }
    }

    private boolean isSessionValid(Session currentSession) {
        return currentSession.getExpiryDate().after(new Date());
    }

    private void injectSession(ContainerRequestContext requestContext, Session session) {
        final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
        requestContext.setSecurityContext(new SecurityContext() {

            @Override
            public Principal getUserPrincipal() {
                return () -> session.getUserId().toString();
            }

            @Override
            public boolean isUserInRole(String role) {
                return session.getRoles().contains(role);
            }

            @Override
            public boolean isSecure() {
                return currentSecurityContext.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return "Cookie";
            }
        });
    }
}
