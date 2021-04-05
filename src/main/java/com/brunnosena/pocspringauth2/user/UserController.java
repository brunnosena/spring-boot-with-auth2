package com.brunnosena.pocspringauth2.user;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends WebSecurityConfigurerAdapter {
	
	@GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
	    System.out.println(principal);

        List<Character> list = new ArrayList<Character>();
        list.add('X');
        list.add('Y');
        System.out.println("Initial list: "+ list);
        List<List<Character>> list2 = Collections.singletonList(list);

//        ArrayList<Object> objects = Collections.singletonMap("name", principal.getAttribute("name"));
        return Collections.singletonMap("name", principal.getAttribute("name"));
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
                .antMatchers("/", "/error", "/webjars/**").permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(e -> e
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .oauth2Login();
        // @formatter:on
    }
	
}
