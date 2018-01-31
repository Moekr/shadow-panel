package com.moekr.shadow.panel.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	public static final GrantedAuthority USER_AUTHORITY = new SimpleGrantedAuthority("USER");
	public static final GrantedAuthority ADMIN_AUTHORITY = new SimpleGrantedAuthority("ADMIN");

	private final UserDetailsService userDetailsService;

	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/api/**").hasAuthority(ADMIN_AUTHORITY.getAuthority())
				.anyRequest().hasAuthority(USER_AUTHORITY.getAuthority())
				.and()
				.httpBasic()
				.and()
				.csrf().disable()
				.cors();
	}
}
