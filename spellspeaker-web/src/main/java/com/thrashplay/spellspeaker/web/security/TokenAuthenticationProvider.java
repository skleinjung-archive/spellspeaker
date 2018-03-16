package com.thrashplay.spellspeaker.web.security;

import com.thrashplay.spellspeaker.model.User;
import com.thrashplay.spellspeaker.repository.UserRepository;
import com.thrashplay.spellspeaker.web.model.AuthenticationToken;
import com.thrashplay.spellspeaker.web.repository.AuthenticationTokenRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author Sean Kleinjung
 */
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private AuthenticationTokenRepository authenticationTokenRepository;
    private UserRepository userRepository;

    public TokenAuthenticationProvider(AuthenticationTokenRepository authenticationTokenRepository, UserRepository userRepository) {
        Assert.notNull(authenticationTokenRepository, "authenticationTokenRepository cannot be null");
        this.authenticationTokenRepository = authenticationTokenRepository;

        Assert.notNull(userRepository, "userRepository cannot be null");
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String tokenValue = (String) authentication.getPrincipal();

        if (tokenValue == null || tokenValue.isEmpty()) {
            throw new BadCredentialsException("Invalid token");
        }

        AuthenticationToken token = authenticationTokenRepository.findByToken(tokenValue);
        if (token == null) {
            throw new BadCredentialsException("Invalid token or token expired");
        }

        // todo: check for expiration

        User user = userRepository.findOne(token.getUserId());
        Credentials credentials = new Credentials();
        credentials.setUsername(user.getUsername());
        credentials.setPassword("[PROTECTED]");
        PreAuthenticatedAuthenticationToken result = new PreAuthenticatedAuthenticationToken(
                new SpellspeakerUserDetails(user),
                credentials
        );
        result.setAuthenticated(true);

        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
