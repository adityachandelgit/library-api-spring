package org.adityachandel.libraryapispring.config;

import lombok.extern.slf4j.Slf4j;
import org.adityachandel.libraryapispring.exception.UnauthorizedException;
import org.adityachandel.libraryapispring.service.SimpleAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Slf4j
public class HttpRequestsInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SimpleAuthenticationService authenticationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (authenticateUrl(request.getRequestURI())) {
            final String[] credentials = getCredentialsFromHeader(request);
            boolean validCredentials = authenticationService.areCredentialsValid(credentials[0], credentials[1]);
            if (!validCredentials) {
                throw new UnauthorizedException("Invalid Credentials!");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
    }


    String[] getCredentialsFromHeader(HttpServletRequest httpRequest) throws Exception {
        final String authorization = httpRequest.getHeader("Authorization");
        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            return credentials.split(":", 2);
        } else {
            throw new UnauthorizedException("Missing request header 'Authorization' for method parameter of type String");
        }
    }

    private boolean authenticateUrl(String incomingUrl) {
        List<String> authenticatedUrls = Arrays.asList("/api/library/**");
        boolean matched = false;
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String authUrl : authenticatedUrls) {
            final boolean match = antPathMatcher.match(authUrl, incomingUrl);
            if (match) {
                matched = true;
                break;
            }
        }
        return matched;
    }


}
