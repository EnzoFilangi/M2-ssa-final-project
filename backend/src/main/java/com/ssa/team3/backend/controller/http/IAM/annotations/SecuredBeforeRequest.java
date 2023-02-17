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
import java.util.Map;
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
        Map<String, Cookie> cookies = requestContext.getCookies();

        Cookie sessionCookie = cookies.get("sessionId");
        if (sessionCookie == null) {
            abortWithUnauthorized(requestContext);
            return;
        }

        Optional<Session> sessionOptional = getSession(sessionCookie);
        if (sessionOptional.isEmpty()){
            abortWithUnauthorized(requestContext);
            return;
        }
        Session currentSession = sessionOptional.get();

        if (isSessionValid(currentSession)){
            injectSession(requestContext, currentSession);
        } else {
            iamService.removeSession(currentSession.getId());
            abortWithUnauthorized(requestContext);
        }
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }

    private Optional<Session> getSession(Cookie sessionCookie) {
        return iamService.getSession(UUID.fromString(sessionCookie.getValue()));
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