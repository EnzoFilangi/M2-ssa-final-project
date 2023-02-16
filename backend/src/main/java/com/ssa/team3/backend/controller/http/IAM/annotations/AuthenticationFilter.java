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
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    @Inject IAMService iamService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Map<String, Cookie> cookies = requestContext.getCookies();

        Cookie sessionCookie = cookies.get("sessionId");
        if (sessionCookie == null) {
            abortWithUnauthorized(requestContext);
            return;
        }

        Optional<Session> currentSession = getSession(sessionCookie);
        if (currentSession.isEmpty()){
            abortWithUnauthorized(requestContext);
            return;
        }

        injectSession(requestContext, currentSession.get());
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }

    private Optional<Session> getSession(Cookie sessionCookie) {
        return iamService.getSession(UUID.fromString(sessionCookie.getValue()));
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
