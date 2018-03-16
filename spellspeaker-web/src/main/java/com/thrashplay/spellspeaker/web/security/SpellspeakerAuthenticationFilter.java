package com.thrashplay.spellspeaker.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thrashplay.spellspeaker.model.User;
import com.thrashplay.spellspeaker.web.model.AuthenticationToken;
import com.thrashplay.spellspeaker.web.repository.AuthenticationTokenRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sean Kleinjung
 */
public class SpellspeakerAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHENTICATE_URL = "/api/auth/login";

    private AuthenticationManager authenticationManager;
    private AuthenticationTokenRepository authenticationTokenRepository;

    public SpellspeakerAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationTokenRepository authenticationTokenRepository) {
        Assert.notNull(authenticationManager, "authenticationManager cannot be null");
        this.authenticationManager = authenticationManager;

        Assert.notNull(authenticationTokenRepository, "authenticationTokenRepository cannot be null");
        this.authenticationTokenRepository = authenticationTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String username = httpServletRequest.getHeader("X-TT-Authentication-Username");
        String password = httpServletRequest.getHeader("X-TT-Authentication-Password");
        String token = httpServletRequest.getHeader("X-TT-Authentication-Token");

        String resourcePath = new UrlPathHelper().getPathWithinApplication(httpServletRequest);

        try {
            if (isAuthenticationAttempt(httpServletRequest, resourcePath)) {
                logger.debug(String.format("Trying to authenticate user %s by X-Auth-Username method", username));
                processUsernamePasswordAuthentication(httpServletResponse, username, password);
                return;
            }

            if (token != null) {
                logger.debug("Trying to authenticate user by token method.");
                processTokenAuthentication(token);
            }

            logger.debug("AuthenticationFilter is passing request down the filter chain");
            // addSessionContextToLogging();
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (InternalAuthenticationServiceException internalAuthenticationServiceException) {
            SecurityContextHolder.clearContext();
            logger.error("Internal authentication service exception", internalAuthenticationServiceException);
            httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (AuthenticationException authenticationException) {
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
        }
    }

    private boolean isAuthenticationAttempt(HttpServletRequest httpRequest, String resourcePath) {
        return AUTHENTICATE_URL.equalsIgnoreCase(resourcePath)
                && httpRequest.getMethod().equals("POST");
    }

    private void processUsernamePasswordAuthentication(HttpServletResponse httpResponse, String username, String password) throws IOException {
        Authentication resultOfAuthentication = tryToAuthenticateWithUsernameAndPassword(username, password);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);

        User user = ((SpellspeakerUserDetails) resultOfAuthentication.getPrincipal()).getAppUser();
        final AuthenticationToken token = authenticationTokenRepository.createTokenFor(user);

        httpResponse.setStatus(HttpServletResponse.SC_OK);
        String tokenJsonResponse = new ObjectMapper().writeValueAsString(new LoginReturnType(token, user));
        httpResponse.addHeader("Content-Type", "application/json");
        httpResponse.getWriter().print(tokenJsonResponse);
    }

    private Authentication tryToAuthenticateWithUsernameAndPassword(String username, String password) {
        if (username == null || password == null) {
            throw new AuthenticationServiceException("Username and password are required for authentication");
        }

        UsernamePasswordAuthenticationToken requestAuthentication = new UsernamePasswordAuthenticationToken(username, password);
        return tryToAuthenticate(requestAuthentication);
    }

    private void processTokenAuthentication(String token) {
        Authentication resultOfAuthentication = tryToAuthenticateWithToken(token);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
    }

    private Authentication tryToAuthenticateWithToken(String token) {
        PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(token, null);
        return tryToAuthenticate(requestAuthentication);
    }

    private Authentication tryToAuthenticate(Authentication requestAuthentication) {
        Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate user for provided credentials");
        }
        logger.debug("User successfully authenticated");
        return responseAuthentication;
    }

    public class LoginReturnType {
        private AuthenticationToken authenticationToken;
        private User user;

        public LoginReturnType(AuthenticationToken token, User user) {
            this.authenticationToken = token;
            this.user = user;
        }

        public AuthenticationToken getAuthenticationToken() {
            return authenticationToken;
        }

        public User getUserProfile() {
            return user;
        }
    }
}
