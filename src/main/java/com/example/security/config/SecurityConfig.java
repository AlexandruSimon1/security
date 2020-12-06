package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(a -> {
				a.mvcMatchers(GET, "/resolutions", "/resolution/**").hasAuthority("READ");
				a.mvcMatchers(POST, "/user").hasAuthority("WRITE");
				a.mvcMatchers(GET, "/admin/user/**", "/admin/users").hasAuthority("ADMIN");
				a.mvcMatchers(GET, "/user/**", "/users").hasAuthority("WRITE");
				a.mvcMatchers(GET,"/user/activation/**").permitAll();
				a.mvcMatchers(POST,"/password/**").permitAll();
				a.anyRequest().hasAuthority("WRITE");
			})
			.httpBasic();


		http.csrf().disable();
	}

	// Allow Spring Security to send error messages
	@Override
	public void configure(WebSecurity webSecurity) throws Exception
	{
		webSecurity
				.ignoring()
				// All of Spring Security will ignore the requests
				.antMatchers("/error/**");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
