package com.samha.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SamhaAuthenticationFilter authenticationFilter = new SamhaAuthenticationFilter(authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**", "/api/auth/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/**").hasAuthority("COORDENADOR_ACADEMICO");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/**").hasAuthority("COORDENADOR_ACADEMICO");
        http.authorizeRequests().antMatchers(HttpMethod.PATCH,"/api/**").hasAuthority("COORDENADOR_ACADEMICO");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/**").hasAuthority("COORDENADOR_ACADEMICO");
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/**").hasAuthority("COORDENADOR_ACADEMICO");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new SamhaAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}