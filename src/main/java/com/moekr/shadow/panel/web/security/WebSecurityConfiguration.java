package com.moekr.shadow.panel.web.security;

import com.moekr.shadow.panel.web.security.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
public class WebSecurityConfiguration {
	public static final GrantedAuthority USER_AUTHORITY = new SimpleGrantedAuthority("USER");
	public static final GrantedAuthority ADMIN_AUTHORITY = new SimpleGrantedAuthority("ADMIN");

	@Configuration
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public static class ApiWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/api/**")
					.authorizeRequests()
					.anyRequest().hasAnyAuthority(ADMIN_AUTHORITY.getAuthority())
					.and()
					.httpBasic()
					.and()
					.csrf().disable()
					.cors();
		}
	}

	@Configuration
	public static class DefaultWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
		private final UserDetailsService userDetailsService;

		@Autowired
		public DefaultWebSecurityConfiguration(UserDetailsServiceImpl userDetailsService) {
			this.userDetailsService = userDetailsService;
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
					.antMatchers("/register.html", "/subscribe", "/js/**", "/css/**", "/favicon.ico").permitAll()
					.antMatchers("/", "/index.html").authenticated()
					.antMatchers("/dashboard/**").hasAuthority(ADMIN_AUTHORITY.getAuthority())
					.anyRequest().hasAuthority(USER_AUTHORITY.getAuthority())
					.and()
					.formLogin().loginPage("/login.html").defaultSuccessUrl("/").failureUrl("/login.html?from=login").permitAll()
					.and()
					.logout().logoutUrl("/logout.html").logoutSuccessUrl("/login.html?from=logout").permitAll()
					.and()
					.rememberMe().rememberMeParameter("remember").rememberMeCookieName("REMEMBER").userDetailsService(userDetailsService)
					.and()
					.csrf().disable();
		}
	}
}
