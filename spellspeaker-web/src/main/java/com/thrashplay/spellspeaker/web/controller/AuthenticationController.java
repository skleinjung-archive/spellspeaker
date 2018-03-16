package com.thrashplay.spellspeaker.web.controller;

import com.thrashplay.spellspeaker.model.User;
import com.thrashplay.spellspeaker.repository.UserRepository;
import com.thrashplay.spellspeaker.web.model.Status;
import com.thrashplay.spellspeaker.web.repository.AuthenticationTokenRepository;
import com.thrashplay.spellspeaker.web.security.Credentials;
import com.thrashplay.spellspeaker.web.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author Sean Kleinjung
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private AuthenticationTokenRepository authenticationTokenRepository;

    @Autowired
    private AuthenticationController(AuthenticationTokenRepository authenticationTokenRepository) {
        Assert.notNull(authenticationTokenRepository, "authenticationTokenRepository cannot be null");
        this.authenticationTokenRepository = authenticationTokenRepository;
    }

    @PostMapping("/logout")
    public Status user(@CurrentUser User user) {
        authenticationTokenRepository.removeTokenFor(user);
        return Status.SUCCESS;
    }
}
