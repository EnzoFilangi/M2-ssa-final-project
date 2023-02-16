package com.ssa.team3.backend.controller.http.IAM.annotations;

import com.ssa.team3.backend.model.domain.IAM.IAMService;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.ext.Provider;
import jakarta.inject.Inject;

import java.util.Map;
import java.util.UUID;

@Secured
@Provider
public class RenewSessionFilter implements ContainerResponseFilter {
    @Inject IAMService iamService;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        Map<String, Cookie> cookies = requestContext.getCookies();

        Cookie sessionCookie = cookies.get("sessionId");
        if (sessionCookie == null) {
            return;
        }

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
