package com.thrashplay.spellspeaker.web.security;

import com.thrashplay.spellspeaker.repository.UserRepository;
import com.thrashplay.spellspeaker.web.repository.AuthenticationTokenRepository;
import com.thrashplay.spellspeaker.web.security.SpellspeakerAuthenticationFilter;
import com.thrashplay.spellspeaker.web.security.SpellspeakerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private SpellspeakerUserDetailsService userService;
    private UserRepository userRepository;
    private AuthenticationTokenRepository authenticationTokenRepository;

    @Autowired
    public SecurityConfig(SpellspeakerUserDetailsService userDetailsService, UserRepository userRepository, AuthenticationTokenRepository authenticationTokenRepository) {
        Assert.notNull(userDetailsService, "userDetailsService cannot be null");
        this.userService = userDetailsService;

        Assert.notNull(userRepository, "userRepository cannot be null");
        this.userRepository = userRepository;

        Assert.notNull(authenticationTokenRepository, "authenticationTokenRepository cannot be null");
        this.authenticationTokenRepository = authenticationTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .antMatchers("/api/auth/login").permitAll()
            .anyRequest().authenticated()

            .and()
            .anonymous().disable().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());

        http.addFilterBefore(new SpellspeakerAuthenticationFilter(authenticationManager(), authenticationTokenRepository), BasicAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userService)
            .and()
            .authenticationProvider(tokenAuthenticationProvider());
//            .inMemoryAuthentication()
//            .withUser("blue").password("password").roles("USER")
//            .and()
//            .withUser("red").password("password").roles("USER");
    }

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Bean
    public AuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider(authenticationTokenRepository, userRepository);
    }
}