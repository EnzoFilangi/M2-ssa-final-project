package com.ssa.team3.backend.controller.http.IAM.annotations;

import com.ssa.team3.backend.model.domain.IAM.IAMService;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.ext.Provider;
import jakarta.inject.Inject;

import java.util.UUID;

/**
 * This class implements the part of the @Secured annotation that happens after the request.
 * If the session cookie exists, it renews it and increases the expiry date of the session in the DB.
 */
@Secured
@Provider
public class SecuredAfterRequest implements ContainerResponseFilter {
    @Inject IAMService iamService;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        Cookie sessionCookie = getSessionCookie(requestContext);
        if (sessionCookie == null) {
            return;
        }

        extendSession(responseContext, sessionCookie);
    }

    private Cookie getSessionCookie(ContainerRequestContext requestContext) {
        return requestContext.getCookies().get("sessionId");
    }

    /**
     * Extends the session by extending the cookie's expiration date and increasing the duration of the session in the DB
     */
    private void extendSession(ContainerResponseContext responseContext, Cookie sessionCookie) {
        responseContext.getHeaders().add("Set-Cookie", extendCookieDuration(sessionCookie));
        iamService.renewSession(UUID.fromString(sessionCookie.getValue()));
    }

    /**
     * Extends the duration of the cookie by 24h
     */
    private NewCookie extendCookieDuration(Cookie cookie){
        return new NewCookie(cookie.getName(), cookie.getValue(), "/", "", "", 60*60*24, true, true);
    }
}
