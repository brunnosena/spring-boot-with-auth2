package com.brunnosena.pocspringauth2.controller;

import java.util.*;

import com.brunnosena.pocspringauth2.contract.User;
import org.apache.tomcat.jni.Error;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends WebSecurityConfigurerAdapter {
	
	@GetMapping("/user")
    public User user(@AuthenticationPrincipal OAuth2User principal) {
	    if (principal == null) {
	        throw new SessionAuthenticationException("fqçefjewg");
        }

	    User user = new User();
	    user.setName(principal.getAttribute("name"));
        user.setAvatarUrl(principal.getAttribute("avatar_url"));
        user.setBio(principal.getAttribute("bio"));
        user.setEmail(principal.getAttribute("email"));
        user.setLogin(principal.getAttribute("login"));

        return user;
    }
	
	@Override	
    protected void configure(HttpSecurity http) throws Exception {
    	// @formatter:off
		http
        // ... existing code here
        .csrf(c -> c
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        );
        
		http
        // ... existing code here
        .logout(l -> l
            .logoutSuccessUrl("/").permitAll()
        );
        
        http
            .authorizeRequests(a -> a
                .antMatchers("/error", "/webjars/**").permitAll()
            )
            .exceptionHandling(e -> e
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .oauth2Login();
        // @formatter:on
    }
	
}
